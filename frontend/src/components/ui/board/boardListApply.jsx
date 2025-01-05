import React, { useState } from "react";
import * as S from "./boardListStyle";
import ModalModify from "../modal/modalModify";
import { truncate } from "../../../utils/truncateText";
import formatRelativeTime from "../../../utils/formatTime";
import {replaceNewlinesWithSpace} from "../../../utils/replaceUtil"

const BoardListApply = ({ boardData, formData }) => {

  const boardId = boardData?.boardId || "1";
  const title = boardData?.title || "제목을 불러오는 중...";
  const content = boardData?.content || "내용을 불러오는 중...";
  const author = boardData?.author?.username || "이유진";
  const createdAt = boardData?.createdAt || "2024.12.26";
  const hit = boardData?.hit || "11";
  const comments = boardData?.comments || "0";
  const applicants = boardData?.applyForms || { frontend: 0, backend: 0, uiux: 0, marketer: 0 };

  const [open, setOpen] = useState(false);

  const modalOpen = (e) => {
    console.log("모달 오픈 클릭!");
    e.stopPropagation(); // 이벤트 전파 중단
    e.preventDefault(); // 기본 동작 방지
    setOpen(true);
  };

  const closeModal = () => {
    setOpen(false); // 모달 닫기
  };

  return (
    <>
    <S.LinkContainer100 to={`/boards/${boardId}`} state={{ boardData }}>
      <S.BoardListContainer>
        <S.RightTop>
          <S.ApplyButton onClick={modalOpen} >지원 폼</S.ApplyButton>
        </S.RightTop>
        <S.Row>
          <S.TitleContainer>
            <S.Badge>모집중</S.Badge>
            <S.Title>{truncate(title, 33)}</S.Title>
          </S.TitleContainer>
        </S.Row>

        <S.Content>{replaceNewlinesWithSpace(truncate(content, 52))}</S.Content>

        <S.InfoContainer>
          <S.InfoItem>
            <div>{author}</div>
          </S.InfoItem>
          <S.InfoItem>
            <div>・ {formatRelativeTime(createdAt)}</div>
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
    <ModalModify isOpen={open} onClose={closeModal} formData={formData} />
    </>
  );
};

export default BoardListApply;
