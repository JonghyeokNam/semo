import React, { useState } from "react";
import * as S from "./boardListStyle";
import { LiaHandPaper } from "react-icons/lia";
import Modal from "../modal/modalWrite";
import { truncate } from "../../../utils/truncateText";
import { replaceNewlinesWithSpace } from "../../../utils/replaceUtil";
import formatRelativeTime from "../../../utils/formatTime";

const BoardListWrite = ({ boardData }) => {
  const boardId = boardData?.boardId || "2";
  const title = boardData?.title || "제목을 불러오는 중...";
  const content = boardData?.content || "내용을 불러오는 중...";
  const author = boardData?.author?.username || "작성자 없음";
  const createdAt = boardData?.createdAt || "작성일 불러오는 중...";
  const hit = boardData?.hit || "0";
  const commentCount = boardData?.commentCount || "0";
  const applyForms = boardData?.applyForms || {
    backend: 0,
    frontend: 0,
    marketer: 0,
    designer: 0,
  };
  const isParticipated = boardData?.isParticipated || false;
  const isClosed = boardData?.isClosed || false;

  const [open, setOpen] = useState(false);

  const modalOpen = (e) => {
    e.stopPropagation(); // 이벤트 전파 중단
    e.preventDefault(); // 기본 동작 방지
    setOpen(true);
  };

  const closeModal = () => {
    setOpen(false); // 모달 닫기
  };

  return (
    <>
      <S.LinkContainer to={`/boards/${boardId}`} state={{ boardData }}>
        <S.BoardListContainer>
          {/* 조건부 스타일링 */}
          <S.RightTop $isParticipated={isParticipated}>
            <LiaHandPaper onClick={modalOpen} />
          </S.RightTop>
          <S.Row>
            <S.TitleContainer>
              <S.Badge>모집중</S.Badge>
              <S.Title>{title}</S.Title>
            </S.TitleContainer>
          </S.Row>

          <S.Content>{truncate(replaceNewlinesWithSpace(content), 80)}</S.Content>

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
              <div>{commentCount}</div>
            </S.InfoItem>
            <S.ApplicantInfo>
              <div>지원자 수 |</div>
              <div>프론트엔드 {applyForms.frontend}명</div>
              <div>백엔드 {applyForms.backend}명</div>
              <div>UI/UX {applyForms.designer}명</div>
              <div>마케터 {applyForms.marketer}명</div>
            </S.ApplicantInfo>
          </S.InfoContainer>
        </S.BoardListContainer>
      </S.LinkContainer>
      <Modal isOpen={open} onClose={closeModal} boardId={boardId} />
    </>
  );
};

export default BoardListWrite;
