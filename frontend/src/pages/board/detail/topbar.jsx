import React from "react";
import * as S from "./style";
import { FaEye } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom";
import { useDeleteBoardStore } from "../../../store/useBoardStore";

export const TopBar = ({ boardInfo }) => {
  const { deleteBoard, loading, error } = useDeleteBoardStore();
  const boardData = boardInfo;
  const username = boardInfo?.author?.username || "작성자 없음";
  const createdAt = boardInfo?.createdAt
    ? new Date(boardInfo.createdAt).toLocaleDateString()
    : "작성일 불러오는 중...";
  const hit = boardInfo?.hit || 0;
  const navigate = useNavigate();

  const handleDelete = async () => {
    if (!boardData || !boardData.boardId) {
      alert("게시글 정보가 없습니다.");
      return;
    }

    const confirmDelete = window.confirm("정말 이 게시글을 삭제하시겠습니까?");
    if (!confirmDelete) return;

    try {
      // store의 deleteBoard 호출
      await deleteBoard(boardData.boardId);
      if (!error) {
        alert("게시물이 삭제되었습니다!");
        navigate("/"); // 삭제 후 이동할 경로
      }
    } catch (err) {
      console.error("삭제 요청 오류:", err);
      alert(`삭제 요청에 실패했습니다: ${err.message}`);
    }
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
