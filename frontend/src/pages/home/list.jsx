import React, { useState, useEffect } from "react";
import BoardList from "../../components/ui/board/boardList";
import { useLocation, useNavigate } from "react-router-dom";
import useBoardListStore from "../../store/useBoardListStore";
import * as S from "./style";  

const List = () => {
  const { boards, fetchBoards, totalPages, currentPage, loading, error } = useBoardListStore();
  const itemsPerPage = 10;

  const navigate = useNavigate(); // 페이지 이동을 위한 useNavigate
  const location = useLocation(); // 현재 URL 정보 가져오기

  // URL에서 page 값 가져오기, 없으면 0으로 설정
  const query = new URLSearchParams(location.search);
  const pageFromUrl = parseInt(query.get("page"), 10) || 0;

  // 페이지 변경 시 호출
  const handlePageChange = (pageNumber) => {
    // URL에 페이지 번호 업데이트
    navigate(`?page=${pageNumber}`);
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  // URL에 맞춰 데이터 Fetch
  useEffect(() => {
    fetchBoards(pageFromUrl, itemsPerPage);
  }, [fetchBoards, pageFromUrl]);

  // 페이지 번호 리스트 생성
  const pageNumbers = [];
  for (let i = 1; i <= totalPages; i++) {
    pageNumbers.push(i);
  }

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <>
       {boards.map((board) => ( 
            <BoardList key={board.boardId} boardData={board} />
        ))}
      {totalPages > 1 && (
        <S.Pagination>
          {pageNumbers.map((number) => (
            <S.PageNumber
              key={number}
              $isActive={number === currentPage}
              onClick={() => handlePageChange(number)}
            >
              {number}
            </S.PageNumber>
          ))}
        </S.Pagination>
      )}
    </>
  );
};

export default List;
