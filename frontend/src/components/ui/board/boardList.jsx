import React from "react";
import * as S from "./boardListStyle";
import { LiaHandPaper } from "react-icons/lia";
import { Link } from "react-router-dom"; 


const BoardList = ({ boardData }) => {
  return (
    <S.LinkContainer to="/board/detail">
    <S.BoardListContainer>
    <S.StopIcon>
      <LiaHandPaper />
    </S.StopIcon>
    <S.Row>
      <S.TitleContainer>
        <S.Badge>모집중</S.Badge>
        <S.Title>{boardData.title || "제목을 불러오는 중..."}</S.Title>
      </S.TitleContainer>
    </S.Row>

    <S.Content>
      {boardData.content || "내용을 불러오는 중..."}
    </S.Content>

    <S.InfoContainer>
      <S.InfoItem>
        <div>{boardData.author.username || "작성자 정보 없음"}</div>
      </S.InfoItem>
      <S.InfoItem>
        <div>・ {boardData.createdAt || "시간 정보 없음"}</div>
      </S.InfoItem>
      <S.InfoItem>
        <S.Icon>
          <S.EyeIcon />
        </S.Icon>
        <div>{boardData.hit}</div>
      </S.InfoItem>
      <S.InfoItem>
        <S.Icon>
          <S.CommentIcon />
        </S.Icon>
        <div>{boardData.comments}</div>
      </S.InfoItem>
      <S.ApplicantInfo>
        <div>지원자 수 |</div>
        <div>프론트엔드 {boardData.applicants.frontend}명</div>
        <div>백엔드 {boardData.applicants.backend}명</div>
        <div>UI/UX {boardData.applicants.uiux}명</div>
        <div>마케터 {boardData.applicants.marketer}명</div>
      </S.ApplicantInfo>
    </S.InfoContainer>
  </S.BoardListContainer>
  </S.LinkContainer>
  );
};

export default BoardList;
