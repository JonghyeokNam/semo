import React, { useState } from "react";
import * as S from "./style";
import { FaEye } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { LiaHandPaper } from "react-icons/lia";
import Modal from "../../../components/ui/modal/modalWrite";
import { useDoBookmarkStore } from "../../../store/useBookmarkStore";

export const TopBarApply = ({boardInfo}) => {
  const { fetchBookmark } = useDoBookmarkStore();
  const username = boardInfo?.author?.username || "작성자 없음";
  const createdAt = boardInfo?.createdAt
  ? new Date(boardInfo.createdAt).toLocaleDateString()
  : "작성일 불러오는 중...";
  const hit = boardInfo?.hit || 0;
  const isbookmarked = boardInfo?.isbookmarked || "false";
  
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const [isActive, setIsActive] = useState(isbookmarked);

  const handleList = () => {
    navigate("/applylist"); // 버튼 클릭 시 '/applylist'로 이동
  };

  const handleModify = () => {
    navigate("/board/modify"); // 버튼 클릭 시 '/board/modify'로 이동
  };

  const handleBookmark = async () => {
    console.log("북마크 클릭!");
    await fetchBookmark(boardInfo.boardId); // 북마크 API 호출
    setIsActive((prev) => !prev); // isActive 상태를 토글
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
          <S.StyledBookmarkIcon $isActive={isActive} />
        </S.IconWrapper>
        <S.IconWrapper onClick={modalOpen}>
          <LiaHandPaper style={{ fontSize: "27px", color: "#333" }} />
        </S.IconWrapper>
      </S.iconContainer>

      <Modal isOpen={open} onClose={closeModal} />
    </S.TopBar>
  );
};
