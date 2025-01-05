import React from "react";
import * as S from "./style";
import { FaEye } from "react-icons/fa"; 
import { Link, useNavigate } from 'react-router-dom';

export const TopBar = ({boardInfo}) => {
  const boardData = boardInfo;
  const username = boardInfo?.author?.username || "작성자 없음";
  const createdAt = boardInfo?.createdAt
  ? new Date(boardInfo.createdAt).toLocaleDateString()
  : "작성일 불러오는 중...";
  const hit = boardInfo?.hit || 0;
    const navigate = useNavigate();

    const handlelist = () => {
        navigate("/applylist"); // 버튼 클릭 시 '/'로 이동
      };

      const handleModify = () => {
        navigate("/board/modify"); // 버튼 클릭 시 '/'로 이동
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
            <FaEye style={{ fontSize: '18px' }} />
            조회수
            {" " + hit}
          </S.ViewCountWrapper>
        </S.Row>
        <S.Row>
          <Link to="/applylist" state={{ boardData }}>
            <S.Status>지원서 목록</S.Status>
          </Link>
          {/* <S.Status>마감</S.Status> */}
          <S.Status onClick={handleModify}>수정</S.Status>
          <S.Status>삭제</S.Status>
        </S.Row>
      </S.TopBar>
    );
  };