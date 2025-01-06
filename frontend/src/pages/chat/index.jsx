import React, { useState } from "react";
import * as S from "./style";
import useMediaQueries from "../../hooks/useMediaQueries";
import NoRoom from "./noRoom";
import ChatList from "./ChatList";
import ChatRoom from "./message/chatRoom";

// 이유진, 남종혁
const Index = () => {
  const { isMobile } = useMediaQueries();

  const [selectedRoom, setSelectedRoom] = useState(null); 

  const handleSelectRoom = (roomId) => {
    setSelectedRoom(roomId); 
  };
  
  return (
    <S.ChatWrapper $isMobile={isMobile}>
      <S.Row $isMobile={isMobile}>
        <S.ListContainer $isMobile={isMobile}>
          <S.Title $isMobile={isMobile} >전체</S.Title>
          <ChatList onSelectRoom={handleSelectRoom} />
        </S.ListContainer>
        <S.RoomContainer $isMobile={isMobile}>
          {selectedRoom ? <ChatRoom roomId={selectedRoom} /> : <NoRoom />} {/* Conditional rendering */}
        </S.RoomContainer>
      </S.Row>
    </S.ChatWrapper>
  );
};

export default Index;
