import React, { useState } from "react";
import * as S from "./style";
import { FaEye } from "react-icons/fa";
import { FaComments } from 'react-icons/fa';
import { FaRegBookmark } from "react-icons/fa6";
import { useNavigate } from "react-router-dom";
import { LiaHandPaper } from "react-icons/lia";
import Modal from "../../../components/ui/modal/modalWrite";
import { API } from "../../../lib/apis/utils/index";

export const TopBarApply = ({boardInfo}) => {
  const username = boardInfo?.author?.username || "작성자 없음";
  const createdAt = boardInfo?.createdAt
  ? new Date(boardInfo.createdAt).toLocaleDateString()
  : "작성일 불러오는 중...";
  const hit = boardInfo?.hit || 0;
  
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);

  // (A) 채팅하기 버튼 클릭 -> 채팅방 생성 API 호출
  const handleCreateChatRoom = () => {
    if (!boardInfo?.boardId) {
      console.error("boardInfo가 없어서 채팅방 생성 불가");
      return;
    }

    API.post(`/chatrooms/createByBoard?boardId=${boardInfo?.boardId}`)
      .then((res) => {
        const { resultCode, result } = res.data;
        if (resultCode === "CREATE_CHATROOM_SUCCESS") {
          const newRoomId = result; // roomId
          console.log("채팅방 생성 완료! roomId =", newRoomId);

          // (B) 생성된 방으로 바로 이동
          //     - 예: /chat 페이지에서 roomId 파라미터를 보고 해당 방 접속
          navigate(`/chat?roomId=${newRoomId}`);
        } else {
          console.error("채팅방 생성 실패:", res.data);
        }
      })
      .catch((err) => {
        console.error("채팅방 생성 중 오류:", err);
      });
  };

  const handleList = () => {
    navigate("/applylist"); // 버튼 클릭 시 '/applylist'로 이동
  };

  const handleModify = () => {
    navigate("/board/modify"); // 버튼 클릭 시 '/board/modify'로 이동
  };

  const handleBookmark = () => {
    console.log("북마크 클릭!");
  };

  const modalOpen = () => {
    console.log("참여하기 클릭!");
    setOpen(true);
  };

  const closeModal = () => {
    setOpen(false); // 모달 닫기
  };

  return (
    <S.TopBar>
      <S.Row>
        <S.UserInfo>
          <S.ImgContainer>
            <S.ProfileImage src="/img/sesacHi.png" alt="profile" />
          </S.ImgContainer>
          <S.UserName>{username}</S.UserName>
        </S.UserInfo>
        <S.VerticalLine />
        <S.Date>{createdAt}</S.Date>
        <S.ViewCountWrapper>
          <FaEye style={{ fontSize: "18px" }} />
          조회수
          {" " + hit}
        </S.ViewCountWrapper>
      </S.Row>
      <S.iconContainer>
        <S.IconWrapper onClick={handleBookmark}>
          <FaRegBookmark style={{ fontSize: "20px", color: "#333" }} />
        </S.IconWrapper>
        <S.IconWrapper onClick={modalOpen}>
          <LiaHandPaper style={{ fontSize: "27px", color: "#333" }} />
        </S.IconWrapper>
        {/* (C) 채팅하기 아이콘 추가 */}
        <S.IconWrapper onClick={handleCreateChatRoom}>
          <FaComments style={{ fontSize: "22px", color: "#333" }} />
        </S.IconWrapper>
      </S.iconContainer>

      <Modal isOpen={open} onClose={closeModal} />
    </S.TopBar>
  );
};
