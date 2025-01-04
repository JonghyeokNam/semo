import React from "react";
import * as S from "./style";
import { truncate } from "../../../utils/truncateText";

// const ListComponent = ({ chatData, onSelectRoom, currentUserName }) => {
//   return (
//     <S.ListContainer>
//       {chatData.map((room, index) => {
//         const otherUser = room.userList?.find((u) => u !== currentUserName) || room.roomName;
//         const lastMsg = room.lastMessage || "";
//         const lastTime = room.lastMessageTime
//           ? new Date(room.lastMessageTime).toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })
//           : "";

//         return (
//           <S.ChatItem key={index} onClick={() => onSelectRoom(room.roomId)}>
//             <S.Group>
//               <S.ImgContainer>
//                 <S.ProfileImage src="/img/sesacHi.png" alt={`${otherUser} 프로필`} />
//               </S.ImgContainer>
//               <S.UserInfo>
//                 <S.UserName>{otherUser}</S.UserName>
//                 <S.Content>{truncate(lastMsg, 25)}</S.Content>
//               </S.UserInfo>
//             </S.Group>
//             <S.Time>{lastTime}</S.Time>
//           </S.ChatItem>
//         );
//       })}
//     </S.ListContainer>
//   );
// };

// export default ListComponent;

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

          {/* 오른쪽 영역: 시간 + (안 읽은 메시지 badge) */}
          <S.RightSide>
            <S.Time>{chat.time}</S.Time>
            {chat.unreadCount > 0 && <S.Badge>{chat.unreadCount}</S.Badge>}
          </S.RightSide>
        </S.ChatItem>
      ))}
    </S.ListContainer>
  );
};

export default ListComponent;
