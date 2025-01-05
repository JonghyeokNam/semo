import React from "react";
import * as S from "./style";
import { FaEye } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom";

export const TopBar = ({ boardInfo }) => {
  const boardData = boardInfo;
  const username = boardInfo?.author?.username || "작성자 없음";
  const createdAt = boardInfo?.createdAt
    ? new Date(boardInfo.createdAt).toLocaleDateString()
    : "작성일 불러오는 중...";
  const hit = boardInfo?.hit || 0;
  const navigate = useNavigate();

  const handleDelete = () => {
    console.log("Delteed");
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
      <S.Row>
        <Link to="/applylist" state={{ boardData }}>
          <S.Status>지원서 목록</S.Status>
        </Link>
        {/* <S.Status>마감</S.Status> */}
        <Link to="/board/modify" state={{ boardData }}>
          <S.Status>수정</S.Status>
        </Link>
          <S.Status onClick={handleDelete}>삭제</S.Status>
      </S.Row>
    </S.TopBar>
  );
};
