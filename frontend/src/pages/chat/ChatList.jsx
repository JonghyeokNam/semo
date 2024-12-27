import React from "react";
import ListComponent from "./list/listComponent";

const ChatList = ( {onSelectRoom}) => {
  const chatData = [
    {
        id: 1,  
        image: "/img/sesacHi.png",
        userName: "이유진",
        content: "안녕하세요 ~~나는 이유진이야. 너는 이름이 뭐니그래그래",
        time: "오후 3:15",
    },
    {
        id: 2,
        image: "/img/sesacHi.png",
        userName: "홍길동",
        content: "메시지 테스트",
        time: "오전 11:45",
    },
    {
        id: 3,
        image: "/img/sesacHi.png",
        userName: "홍길동",
        content: "메시지 테스트",
        time: "오전 11:45",
      },
  ];

  return <ListComponent chatData={chatData} onSelectRoom={onSelectRoom} />;
};

export default ChatList;
