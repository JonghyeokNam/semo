import React, { useState } from "react";
import * as S from "./style";
import { FaEye } from "react-icons/fa";
import { FaRegBookmark } from "react-icons/fa6";
import { useNavigate } from "react-router-dom";
import { LiaHandPaper } from "react-icons/lia";
import Modal from "../../../components/ui/modal/modalWrite";

export const TopBarApply = () => {
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);

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
          <S.UserName>사용자 이름</S.UserName>
        </S.UserInfo>
        <S.VerticalLine />
        <S.Date>2024.09.30</S.Date>
        <S.ViewCountWrapper>
          <FaEye style={{ fontSize: "18px" }} />
          조회수
        </S.ViewCountWrapper>
      </S.Row>
      <S.iconContainer>
        <S.IconWrapper onClick={handleBookmark}>
          <FaRegBookmark style={{ fontSize: "20px", color: "#333" }} />
        </S.IconWrapper>
        <S.IconWrapper onClick={modalOpen}>
          <LiaHandPaper style={{ fontSize: "27px", color: "#333" }} />
        </S.IconWrapper>
      </S.iconContainer>

      <Modal isOpen={open} onClose={closeModal} />
    </S.TopBar>
  );
};
