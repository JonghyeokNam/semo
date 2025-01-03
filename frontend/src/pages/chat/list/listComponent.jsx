import React from "react";
import * as S from "./style";
import { truncate } from "../../../utils/truncateText";

const ListComponent = ({ chatData, onSelectRoom }) => {
  return (
    <S.ListContainer>
      {chatData.map((chat, index) => (
        <S.ChatItem key={index} onClick={() => onSelectRoom(chat.id)}>
          <S.Group>
            <S.ImgContainer>
              <S.ProfileImage src={chat.image} alt={`${chat.userName} 프로필`} />
            </S.ImgContainer>
            <S.UserInfo>
              <S.UserName>{chat.userName}</S.UserName>
              <S.Content>{truncate(chat.content, 25)}</S.Content>
            </S.UserInfo>
          </S.Group>
          <S.Time>{chat.time}</S.Time>
        </S.ChatItem>
      ))}
    </S.ListContainer>
  );
};

export default ListComponent;
