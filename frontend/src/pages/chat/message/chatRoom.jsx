import React, { useState, useEffect, useRef } from "react";
import * as S from "./style";
import useMediaQueries from "../../../hooks/useMediaQueries";

import { Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import axios from "axios";

const ChatRoom = ({ roomId }) => {
  const { isMobile } = useMediaQueries();

  // 입력 중인 메시지
  const [message, setMessage] = useState("");
  // 화면에 표시할 메시지 목록
  const [messages, setMessages] = useState([]);

  // STOMP 클라이언트를 담아둘 ref
  const wsClientRef = useRef(null);
  // 채팅 스크롤 하단 처리를 위한 ref
  const messagesEndRef = useRef(null);

  // 1) 방 선택 시: 기존 메시지 불러오기 + 소켓 연결
  useEffect(() => {
    // roomId가 없다면 실행 X
    if (!roomId) return;

    // (1) 먼저 과거 메시지 불러오기 (REST API)
    axios
      .get(`http://localhost:8080/chatrooms/chats?roomId=${roomId}`)
      .then((response) => {
        const { resultCode, result } = response.data;
        if (resultCode === "GET_CHATLIST_SUCCESS") {
          // result: MessageDto[] 형태 (roomId, sender, content, time 등)
          // UI에서 사용하기 좋도록 변환
          const formatted = result.map((msg) => ({
            userName: msg.sender,
            content: msg.content,
            time: new Date(msg.time).toLocaleTimeString([], {
              hour: "2-digit",
              minute: "2-digit",
            }),
            // (임시 로직) 본인인지 여부 판단
            isUser: msg.sender === "남종혁",
          }));
          setMessages(formatted);
        } else {
          console.error("Failed to load chat messages");
        }
      })
      .catch((error) => {
        console.error(error);
      });

    // (2) WebSocket + STOMP 연결
    const sock = new WebSocket("ws://localhost:8080/ws");
    const stompClient = Stomp.over(sock);

    stompClient.connect({}, () => {
      // 연결 성공 후, 특정 채팅방을 subscribe
      stompClient.subscribe(`/sub/chat/room/${roomId}`, (msg) => {
        // 서버에서 전달된 메시지를 수신
        const payload = JSON.parse(msg.body);
        // 예: { roomId, sender, content, time, ... }

        setMessages((prev) => [
          ...prev,
          {
            userName: payload.sender,
            content: payload.content,
            time: new Date(payload.time).toLocaleTimeString([], {
              hour: "2-digit",
              minute: "2-digit",
            }),
            isUser: payload.sender === "남종혁",
          },
        ]);
      });

      // (선택) 방 입장 메시지 전송
      stompClient.send(
        `/pub/chat.enter.${roomId}`,
        {},
        JSON.stringify({ roomId, sender: "남종혁" })
      );
    });

    // stompClient를 ref에 저장
    wsClientRef.current = stompClient;

    // cleanup: roomId가 바뀌거나 컴포넌트 언마운트 시 소켓 해제
    return () => {
      if (wsClientRef.current) {
        wsClientRef.current.disconnect();
      }
    };
  }, [roomId]);

  // 2) 메시지가 갱신될 때마다 자동으로 스크롤 하단으로 이동
  useEffect(() => {
    if (messagesEndRef.current) {
      messagesEndRef.current.scrollIntoView({ behavior: "smooth", block: "end" });
    }
  }, [messages]);

  // 3) 메시지 전송
  const handleSendMessage = (e) => {
    e.preventDefault();
    if (!message.trim()) return;

    // STOMP 연결이 살아있는지 확인
    if (wsClientRef.current && wsClientRef.current.connected) {
      // 서버로 메시지 발행
      wsClientRef.current.send(
        `/pub/chat.message.${roomId}`,
        {},
        JSON.stringify({
          roomId,
          sender: "남종혁", // 로그인 사용자명으로 대체 필요
          content: message,
        })
      );
      // 입력창 비우기
      setMessage("");
    }
  };

  return (
    <S.ChatRoomContainer>
      {/* 메시지 목록 */}
      <S.MessagesContainer $isMobile={isMobile}>
        {messages.map((m, idx) => (
          <S.Message key={idx}>
            <S.UserName isUser={m.isUser}>{m.userName}</S.UserName>
            <S.ContentContainer isUser={m.isUser}>
              <S.Content>
                {/* 말풍선 꼬리 (왼쪽/오른쪽) */}
                {m.isUser ? <S.UserTail /> : <S.Tail />}
                {m.content}
              </S.Content>
              <S.Time>{m.time}</S.Time>
            </S.ContentContainer>
          </S.Message>
        ))}
        {/* 채팅 스크롤 자동 이동을 위한 ref */}
        <div ref={messagesEndRef} />
      </S.MessagesContainer>

      {/* 입력창 + 전송 버튼 */}
      <S.MessageInputContainer>
        <S.MessageInput
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          placeholder="메시지를 입력하세요..."
        />
        <S.SendButton onClick={handleSendMessage}>전송</S.SendButton>
      </S.MessageInputContainer>
    </S.ChatRoomContainer>
  );
};

export default ChatRoom;




// const sampleMessages = [
//   { userName: "이유진", content: "안녕하세요, 대화 시작합니다!", time: "오후 3:16", isUser: true },
//   { userName: "홍길동", content: "네, 안녕하세요!", time: "오후 3:17", isUser: false },
//   { userName: "이유진", content: "안녕하세요, 대화 시작합니다!", time: "오후 3:16", isUser: true },
//   { userName: "홍길동", content: "네, 안녕하세요!!!!!!", time: "오후 3:17", isUser: false },
//   { userName: "홍길동", content: "네, 안녕하세요!", time: "오후 3:17", isUser: false },
//   { userName: "홍길동", content: "네, 안녕하세요!", time: "오후 3:17", isUser: false },
//   { userName: "이유진", content: "안녕하세요, 대화 시작합니다!", time: "오후 3:16", isUser: true },
//   { userName: "이유진", content: "안녕하세요, 대화 시작합니다!", time: "오후 3:16", isUser: true },
// ];

// const ChatRoom = ({ roomId }) => {
//   const { isMobile } = useMediaQueries();
//   const [message, setMessage] = useState("");
//   const [messages, setMessages] = useState(sampleMessages);

//   const handleInputChange = (e) => {
//     setMessage(e.target.value);
//   };

//   const handleSendMessage = (e) => {
//     e.preventDefault();
//     if (message.trim()) {
//       const newMessage = {
//         userName: "이유진", // Assuming the current user is "이유진"
//         content: message,
//         time: new Date().toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" }),
//         isUser: true,
//       };
//       setMessages([...messages, newMessage]);
//       setMessage(""); // Clear input field
//     }
//   };

//   const messagesEndRef = useRef(null);

//   useEffect(() => {
//     // 메시지가 변경될 때마다 마지막 요소로 스크롤
//     if (messagesEndRef.current) {
//       messagesEndRef.current.scrollIntoView({ behavior: 'smooth', block: 'end' });
//     }
//   }, [messages]); // messages 배열이 변경될 때마다 실행

//   return (
//     <S.ChatRoomContainer>
//       <S.MessagesContainer $isMobile={isMobile}>
//         {messages.map((message, index) => (
//           <S.Message key={index}>
//             <S.UserName isUser={message.isUser}>{message.userName}</S.UserName>
//             <S.ContentContainer isUser={message.isUser}>
//               <S.Content>
//                 {message.isUser ? <S.UserTail /> : <S.Tail />}
//                 {message.content}
//               </S.Content>
//               <S.Time>{message.time}</S.Time>
//             </S.ContentContainer>
//           </S.Message>
//         ))}
//         <div ref={messagesEndRef} />
//       </S.MessagesContainer>
//       <S.MessageInputContainer>
//         <S.MessageInput
//           value={message}
//           onChange={handleInputChange}
//           placeholder="메시지를 입력하세요..."
//         />
//         <S.SendButton onClick={handleSendMessage}>전송</S.SendButton>
//       </S.MessageInputContainer>
//     </S.ChatRoomContainer>
//   );
// };

// export default ChatRoom;
