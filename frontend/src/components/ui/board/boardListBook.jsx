import React, { useState } from "react";
import * as S from "./boardListStyle";
import { Link } from "react-router-dom"; 

const BoardListBook = ({ boardData }) => {

  const title = boardData?.title || "제목을 불러오는 중...";
  const content = boardData?.content || "내용을 불러오는 중...";
  const author = boardData?.author?.username || "이유진";
  const createdAt = boardData?.createdAt || "2024.12.26";
  const hit = boardData?.hit || "11";
  const comments = boardData?.comments || "0";
  const applicants = boardData?.applicants || { frontend: 0, backend: 0, uiux: 0, marketer: 0 };

  const [isActive, setIsActive] = useState(true);

  const handleClick = (e) => {
    e.stopPropagation(); // 이벤트 전파 중단
    e.preventDefault(); // 기본 동작 방지
    setIsActive(!isActive); // 상태 토글
  };

  return (
    <S.LinkContainer100 to="/board/detail" state={{ boardData }}>
      <S.BoardListContainer>
        <S.RightTop>
            <S.IconWrapper onClick={handleClick}>
                <S.StyledBookmarkIcon isActive={isActive} />
            </S.IconWrapper>
        </S.RightTop>
        <S.Row>
          <S.TitleContainer>
            <S.Badge>모집중</S.Badge>
            <S.Title>{title}</S.Title>
          </S.TitleContainer>
        </S.Row>

        <S.Content>{content}</S.Content>

        <S.InfoContainer>
          <S.InfoItem>
            <div>{author}</div>
          </S.InfoItem>
          <S.InfoItem>
            <div>・ {createdAt}</div>
          </S.InfoItem>
          <S.InfoItem>
            <S.Icon>
              <S.EyeIcon />
            </S.Icon>
            <div>{hit}</div>
          </S.InfoItem>
          <S.InfoItem>
            <S.Icon>
              <S.CommentIcon />
            </S.Icon>
            <div>{comments}</div>
          </S.InfoItem>
          <S.ApplicantInfo>
            <div>지원자 수 |</div>
            <div>프론트엔드 {applicants.frontend}명</div>
            <div>백엔드 {applicants.backend}명</div>
            <div>UI/UX {applicants.uiux}명</div>
            <div>마케터 {applicants.marketer}명</div>
          </S.ApplicantInfo>
        </S.InfoContainer>
      </S.BoardListContainer>
    </S.LinkContainer100>
  );
};

export default BoardListBook;
