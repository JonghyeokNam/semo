import React, { useState, useEffect, useRef } from "react";
import * as S from "./style";
import useMediaQueries from "../../../hooks/useMediaQueries";

import { Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import axios from "axios";
import { API } from "../../../lib/apis/utils";        // Interceptor
import { useAuthStore } from "../../../store/useAuthStore";
import { useChatStore } from "../../../store/useChatStore"; 
import { useNavigate } from "react-router-dom";
import { FaArrowLeft } from "react-icons/fa";

const ChatRoom = ({ roomId }) => {
  const navigate = useNavigate();
  const { isMobile } = useMediaQueries();

  // 입력 중인 메시지
  const [message, setMessage] = useState("");
  // 화면에 표시할 메시지 목록
  const [messages, setMessages] = useState([]);

  // STOMP 클라이언트를 담아둘 ref
  const wsClientRef = useRef(null);
  // 채팅 스크롤 하단 처리를 위한 ref
  const messagesEndRef = useRef(null);

  // Zustand 스토어에서 user 정보 가져옴
  const { user, isLoggedIn, fetchUserInfo } = useAuthStore((state) => state);

  // (A) 방 정보 세팅 (유저 정보 없으면 불러오기)
  useEffect(() => {
    if (!roomId) return;
    if (isLoggedIn && (!user || !user.username)) {
      // 아직 사용자 정보가 없다면 가져오기
      fetchUserInfo(); 
    }
  }, [roomId, isLoggedIn, user, fetchUserInfo]);

  // (B) 채팅방 초기 설정: 읽음 처리 + 과거 메시지 + 웹소켓 연결
  useEffect(() => {
    if (!roomId) return;

    const currentUserName = user?.username;
    if (!currentUserName) {
      // 유저명이 아직 없다면 대기 (fetchUserInfo 후 재렌더링되면 실행됨)
      return;
    }

    // (B1) "이 방에 있는 모든 메시지"를 우선 읽음 처리
    API.post(`/chatrooms/read?roomId=${roomId}`, {})
      .then((res) => {
        if (res.data.resultCode === "READ_ROOM_SUCCESS") {
          console.log("방 진입 시 읽음 처리 완료");
        }
      })
      .catch((err) => console.error(err));

    // (B2) 과거 메시지 가져오기
    API.get(`/chatrooms/chats?roomId=${roomId}`)
      .then((response) => {
        const { resultCode, result } = response.data;
        if (resultCode === "GET_CHATLIST_SUCCESS") {
          // result: MessageDto[] (roomId, sender, content, time, ...)
          const formatted = result.map((msg) => ({
            userName: msg.sender,
            content: msg.content,
            time: new Date(msg.time).toLocaleTimeString([], {
              hour: "2-digit",
              minute: "2-digit",
            }),
            isUser: msg.sender === currentUserName,
          }));
          setMessages(formatted);
        } else {
          console.error("Failed to load chat messages");
        }
      })
      .catch((error) => {
        console.error(error);
      });

    // (B3) WebSocket (STOMP) 연결
    const sock = new WebSocket("ws://localhost:8080/ws");
    const stompClient = Stomp.over(sock);

    stompClient.connect({}, () => {
      // 연결 성공
      // (1) 방 구독
      stompClient.subscribe(`/sub/chat/room/${roomId}`, (msg) => {
        const payload = JSON.parse(msg.body);

        // 새 메시지를 messages 배열에 추가
        setMessages((prev) => [
          ...prev,
          {
            userName: payload.sender,
            content: payload.content,
            time: new Date(payload.time).toLocaleTimeString([], {
              hour: "2-digit",
              minute: "2-digit",
            }),
            isUser: payload.sender === currentUserName,
          },
        ]);

        // (2) 상대방(내가 아닌 사람)이 보낸 메시지라면 → 즉시 읽음 처리
        if (payload.sender !== currentUserName) {
          API.post(`/chatrooms/read?roomId=${roomId}`, {})
            .then((res) => {
              if (res.data.resultCode === "READ_ROOM_SUCCESS") {
                console.log("상대방 메시지 즉시 읽음 처리");
              }
            })
            .catch((err) => console.error("readRoom Error", err));
        }
      });

      // (2) 방 입장 메시지 (optional)
      stompClient.send(
        `/pub/chat.enter.${roomId}`,
        {},
        JSON.stringify({
          roomId,
          sender: currentUserName,
        })
      );
    });

    wsClientRef.current = stompClient;

    // (B4) cleanup
    return () => {
      if (wsClientRef.current) {
        wsClientRef.current.disconnect();
      }
    };
  }, [roomId, user]);

  // (C) 스크롤 하단 처리
  useEffect(() => {
    if (messagesEndRef.current) {
      messagesEndRef.current.scrollIntoView({ behavior: "smooth" });
    }
  }, [messages]);

  // (D) 메시지 전송
  const handleSendMessage = (e) => {
    e.preventDefault();
    if (!message.trim()) return;

    const currentUserName = user?.username;
    if (wsClientRef.current && wsClientRef.current.connected) {
      // (D1) 메시지 발행
      wsClientRef.current.send(
        `/pub/chat.message.${roomId}`,
        {},
        JSON.stringify({
          roomId,
          sender: currentUserName,
          content: message,
        })
      );

      // (D2) 내가 보낸 메시지도 "나 바로 읽음" 처리를 위해
      API.post(`/chatrooms/read?roomId=${roomId}`, {})
        .then((res) => {
          if (res.data.resultCode === "READ_ROOM_SUCCESS") {
            console.log("내 메시지도 즉시 읽음 처리");
          }
        })
        .catch(console.error);

      setMessage("");
    }
  };

  // (E) 채팅방 나가기
  const handleLeaveRoom = () => {
    if (!roomId) return;
    // 1) 서버 측에 POST 요청
    API.post(`/chatrooms/leave?roomId=${roomId}`, {})
      .then((res) => {
        if (res.data.resultCode === "LEAVE_ROOM_SUCCESS") {
          console.log("방 나가기 성공!", roomId);
          // 2) 웹소켓 연결 해제
          if (wsClientRef.current) {
            wsClientRef.current.disconnect();
          }
          // 3) 채팅 목록 또는 다른 페이지로 이동
          navigate("/chat"); 
        } else {
          console.error("나가기 실패:", res.data);
        }
      })
      .catch((err) => {
        console.error("나가기 중 오류:", err);
      });
  };

  return (
    <S.ChatRoomContainer>
      {/* 상단 바 + 나가기 버튼 */}
      <S.MessagesContainer $isMobile={isMobile}>
        <S.TopBar>
          <S.LeaveButton onClick={handleLeaveRoom}>
            <FaArrowLeft /> 나가기
          </S.LeaveButton>
        </S.TopBar>
        <br />

        {/* 메시지 목록 */}
        {messages.map((m, idx) => (
          <S.Message key={idx}>
            <S.UserName isUser={m.isUser}>{m.userName}</S.UserName>
            <S.ContentContainer isUser={m.isUser}>
              <S.Content>
                {m.isUser ? <S.UserTail /> : <S.Tail />}
                {m.content}
              </S.Content>
              <S.Time>{m.time}</S.Time>
            </S.ContentContainer>
          </S.Message>
        ))}
        <div ref={messagesEndRef} />
      </S.MessagesContainer>

      {/* 입력창 */}
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