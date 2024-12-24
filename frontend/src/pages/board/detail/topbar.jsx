import React from "react";
import * as S from "./style";
import { FaEye } from "react-icons/fa"; 
import { useNavigate } from 'react-router-dom';


export const TopBar = () => {
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
            <S.UserName>사용자 이름</S.UserName>
          </S.UserInfo>
          <S.VerticalLine />
          <S.Date>2024.09.30</S.Date>
          <S.ViewCountWrapper>
            <FaEye style={{ fontSize: '18px' }} />
            조회수
          </S.ViewCountWrapper>
        </S.Row>
        <S.Row>
          <S.Status onClick={handlelist}>지원리스트</S.Status>
          <S.Status>마감</S.Status>
          <S.Status onClick={handleModify}>수정</S.Status>
          <S.Status>삭제</S.Status>
        </S.Row>
      </S.TopBar>
    );
  };