import ListComponent from "./list/listComponent";

import React, { useState, useEffect, useCallback } from "react";
import axios from "axios";
import { useAuthStore } from "../../store/useAuthStore"; 
import { API } from "../../lib/apis/utils/index";
import { useChatStore } from "../../store/useChatStore";
import { Stomp } from "@stomp/stompjs"; 

// 이유진, 남종혁
const ChatList = ({ onSelectRoom }) => {
  const [chatData, setChatData] = useState([]);
  
  // useAuthStore에서 유저 정보와 메서드 가져오기
  const { user, isLoggedIn, fetchUserInfo } = useAuthStore((state) => state);

  /**
   * (1) 채팅방 목록 불러오는 함수: fetchChatRooms
   * - 이 함수를 주기적으로 호출하여 목록 자동 갱신
   */
  const fetchChatRooms = useCallback(() => {
    if (!isLoggedIn) return;
    // 아직 user 정보가 없을 수도 있으니, user?.username이 없으면 일단 무시
    if (!user || !user.username) return;

    API.get("/chatrooms/")
      .then((response) => {
        const { resultCode, result } = response.data;
        if (resultCode === "GET_CHATROOMLIST_SUCCESS") {
          const currentUserName = user.username;

          // result: ChatRoomDto[] 
          // 예: { roomId, roomName, userCount, userList, lastMessage, lastMessageTime, unreadCount }
          const mappedData = result.map((room) => {
            const otherUser =
              room.userList.find((u) => u !== currentUserName) || room.roomName;
            const lastMsg = room.lastMessage || "";
            const lastTime = room.lastMessageTime
              ? new Date(room.lastMessageTime).toLocaleTimeString([], {
                  hour: "2-digit",
                  minute: "2-digit",
                })
              : "";

            return {
              id: room.roomId,
              image: "/img/sesacHi.png", // 임의의 프로필 이미지
              userName: otherUser,
              content: lastMsg,
              time: lastTime,
              unreadCount: room.unreadCount || 0,
            };
          });
          setChatData(mappedData);
        } else {
          console.error("Failed to load chat rooms");
        }
      })
      .catch((error) => {
        console.error("Error while fetching chatrooms:", error);
      });
  }, [isLoggedIn, user]);

  /**
   * (2) 초기 렌더링 시: 유저 정보가 없으면 fetchUserInfo 호출
   *  - 그 후, 바로 fetchChatRooms() 한 번 실행
   *  - setInterval()을 사용해 5초마다 fetchChatRooms()를 재호출
   */
  useEffect(() => {
    if (isLoggedIn && (!user || !user.username)) {
      fetchUserInfo();
    }

    // 즉시 1회 조회
    fetchChatRooms();

    // 2초 간격으로 목록 재조회
    const intervalId = setInterval(() => {
      fetchChatRooms();
    }, 2000);

    // 언마운트 시 interval 해제
    return () => clearInterval(intervalId);

    // 의존성: 유저 / 로그인 상태 등이 바뀌면 다시 실행
  }, [isLoggedIn, user, fetchUserInfo, fetchChatRooms]);

  return <ListComponent chatData={chatData} onSelectRoom={onSelectRoom} />;
};

export default ChatList;
