// import React from "react";
import ListComponent from "./list/listComponent";

import React, { useState, useEffect } from "react";
import axios from "axios";

const ChatList = ({ onSelectRoom }) => {
  const [chatData, setChatData] = useState([]);

  // 가정: 현재 로그인된 사용자를 저장해둔다고 치자
  const currentUser = "남종혁";

  useEffect(() => {
    axios
      .get("http://localhost:8080/chatrooms/")
      .then((response) => {
        const { resultCode, result } = response.data; 
        if (resultCode === "GET_CHATROOMLIST_SUCCESS") {
          // result는 ChatRoomDto[] 리스트
          // 현재 구조: { roomId, roomName, userCount, userList }
  
          // ListComponent에서 쓰던 'id', 'image', 'userName', 'content', 'time' 구조에 맞게 변환
          const mappedData = result.map((room) => {
            // 1) 상대방 닉네임 찾기
            // userList는 2명만 있다고 가정
            const otherUser = room.userList.find(
              (u) => u !== currentUser
            ) || room.roomName;
            // 혹시라도 방에 2명이 아니면 roomName 사용 (fallback)

            // (2) 마지막 메시지, 시간
            const lastMsg = room.lastMessage || "";
            const lastTime = room.lastMessageTime
              ? new Date(room.lastMessageTime).toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })
              : "";

            // 2) 현재는 백엔드가 마지막 메시지나 미확인 메시지 정보를 주지 않으므로
            //    임시로 빈 값/기본값으로 채워둠
            return {
              id: room.roomId,               // onSelectRoom에 넘길 roomId
              image: "/img/sesacHi.png",     // 임의의 기본 이미지 경로
              userName: otherUser,       // 방 이름을 사용하거나 적절히 처리
              content: lastMsg,                   // 아직 '마지막 메시지'가 없다면 빈 값
              time: lastTime,                      // 시간 정보가 없다면 비워둠
              unreadCount: room.unreadCount || 0,
            };
          });
  
          setChatData(mappedData);
        } else {
          console.error("Failed to load chat rooms");
        }
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);
  

  return <ListComponent chatData={chatData} onSelectRoom={onSelectRoom} />;
};

export default ChatList;
