// import React from "react";
import ListComponent from "./list/listComponent";

import React, { useState, useEffect } from "react";
import axios from "axios";

const ChatList = ({ onSelectRoom }) => {
  const [chatData, setChatData] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/chatrooms/")
      .then((response) => {
        const { resultCode, result } = response.data; 
        if (resultCode === "GET_CHATROOMLIST_SUCCESS") {
          // result는 ChatRoomDto[] 리스트
          // 현재 구조: { roomId, roomName, userCount, userList }
  
          // ListComponent에서 쓰던 'id', 'image', 'userName', 'content', 'time' 구조에 맞게 변환
          const mappedData = result.map((room) => {
            return {
              id: room.roomId,               // onSelectRoom에 넘길 roomId
              image: "/img/sesacHi.png",     // 임의의 기본 이미지 경로
              userName: room.roomName,       // 방 이름을 사용하거나 적절히 처리
              content: "",                   // 아직 '마지막 메시지'가 없다면 빈 값
              time: "",                      // 시간 정보가 없다면 비워둠
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

// const ChatList = ( {onSelectRoom}) => {
//   const chatData = [
//     {
//         id: 1,  
//         image: "/img/sesacHi.png",
//         userName: "이유진",
//         content: "안녕하세요 ~~나는 이유진이야. 너는 이름이 뭐니그래그래",
//         time: "오후 3:15",
//     },
//     {
//         id: 2,
//         image: "/img/sesacHi.png",
//         userName: "홍길동",
//         content: "메시지 테스트",
//         time: "오전 11:45",
//     },
//     {
//         id: 3,
//         image: "/img/sesacHi.png",
//         userName: "홍길동",
//         content: "메시지 테스트",
//         time: "오전 11:45",
//       },
//   ];

//   return <ListComponent chatData={chatData} onSelectRoom={onSelectRoom} />;
// };

// export default ChatList;
