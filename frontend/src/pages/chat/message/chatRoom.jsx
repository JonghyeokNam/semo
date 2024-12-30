import React, { useState, useEffect, useRef } from "react";
import * as S from "./style";
import useMediaQueries from "../../../hooks/useMediaQueries";

const sampleMessages = [
  { userName: "이유진", content: "안녕하세요, 대화 시작합니다!", time: "오후 3:16", isUser: true },
  { userName: "홍길동", content: "네, 안녕하세요!", time: "오후 3:17", isUser: false },
  { userName: "이유진", content: "안녕하세요, 대화 시작합니다!", time: "오후 3:16", isUser: true },
  { userName: "홍길동", content: "네, 안녕하세요!!!!!!", time: "오후 3:17", isUser: false },
  { userName: "홍길동", content: "네, 안녕하세요!", time: "오후 3:17", isUser: false },
  { userName: "홍길동", content: "네, 안녕하세요!", time: "오후 3:17", isUser: false },
  { userName: "이유진", content: "안녕하세요, 대화 시작합니다!", time: "오후 3:16", isUser: true },
  { userName: "이유진", content: "안녕하세요, 대화 시작합니다!", time: "오후 3:16", isUser: true },
];

const ChatRoom = ({ roomId }) => {
  const { isMobile } = useMediaQueries();
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState(sampleMessages);

  const handleInputChange = (e) => {
    setMessage(e.target.value);
  };

  const handleSendMessage = (e) => {
    e.preventDefault();
    if (message.trim()) {
      const newMessage = {
        userName: "이유진", // Assuming the current user is "이유진"
        content: message,
        time: new Date().toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" }),
        isUser: true,
      };
      setMessages([...messages, newMessage]);
      setMessage(""); // Clear input field
    }
  };

  const messagesEndRef = useRef(null);

  useEffect(() => {
    // 메시지가 변경될 때마다 마지막 요소로 스크롤
    if (messagesEndRef.current) {
      messagesEndRef.current.scrollIntoView({ behavior: 'smooth', block: 'end' });
    }
  }, [messages]); // messages 배열이 변경될 때마다 실행

  return (
    <S.ChatRoomContainer>
      <S.MessagesContainer $isMobile={isMobile}>
        {messages.map((message, index) => (
          <S.Message key={index}>
            <S.UserName isUser={message.isUser}>{message.userName}</S.UserName>
            <S.ContentContainer isUser={message.isUser}>
              <S.Content>
                {message.isUser ? <S.UserTail /> : <S.Tail />}
                {message.content}
              </S.Content>
              <S.Time>{message.time}</S.Time>
            </S.ContentContainer>
          </S.Message>
        ))}
        <div ref={messagesEndRef} />
      </S.MessagesContainer>
      <S.MessageInputContainer>
        <S.MessageInput
          value={message}
          onChange={handleInputChange}
          placeholder="메시지를 입력하세요..."
        />
        <S.SendButton onClick={handleSendMessage}>전송</S.SendButton>
      </S.MessageInputContainer>
    </S.ChatRoomContainer>
  );
};

export default ChatRoom;
