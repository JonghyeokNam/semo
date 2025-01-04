import React, { useState, useEffect } from "react";
import BoardList from "../../components/ui/board/boardList";
import { useLocation, useNavigate } from "react-router-dom";
import useBoardListStore from "../../store/useBoardListStore";
import * as S from "./style";  

const List = () => {
  const { boards, fetchBoards, totalPages, currentPage, loading, error } = useBoardListStore();
  const itemsPerPage = 10;
  const maxPageNumbersToShow = 10; // 최대 표시 페이지 수

  const navigate = useNavigate(); // 페이지 이동을 위한 useNavigate
  const location = useLocation(); // 현재 URL 정보 가져오기

  // URL에서 page 값 가져오기, 없으면 1로 설정
  const query = new URLSearchParams(location.search);
  const pageFromUrl = parseInt(query.get("page"), 10) || 1;

  const [startPage, setStartPage] = useState(1); // 페이지 그룹의 시작 페이지 번호

  // 페이지 변경 시 호출
  const handlePageChange = (pageNumber) => {
    navigate(`?page=${pageNumber}`);
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  // 다음 그룹 페이지로 이동
  const handleNextGroup = () => {
    const nextStartPage = startPage + maxPageNumbersToShow;
    if (nextStartPage <= totalPages) {
      setStartPage(nextStartPage);
      handlePageChange(nextStartPage);
    }
  };

  // 이전 그룹 페이지로 이동
  const handlePreviousGroup = () => {
    const prevStartPage = startPage - maxPageNumbersToShow;
    if (prevStartPage > 0) {
      setStartPage(prevStartPage);
      handlePageChange(prevStartPage);
    }
  };

  // URL에 맞춰 데이터 Fetch
  useEffect(() => {
    fetchBoards(pageFromUrl, itemsPerPage);
    const newStartPage = Math.floor((pageFromUrl - 1) / maxPageNumbersToShow) * maxPageNumbersToShow + 1;
    setStartPage(newStartPage); // 현재 페이지에 맞춰 시작 페이지 설정
  }, [fetchBoards, pageFromUrl]);

  // 현재 그룹의 페이지 번호 리스트 생성
  const pageNumbers = [];
  for (
    let i = startPage;
    i < startPage + maxPageNumbersToShow && i <= totalPages;
    i++
  ) {
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
          {startPage > 1 && (
            <S.PageButton onClick={handlePreviousGroup}>{"<"}</S.PageButton>
          )}
          {pageNumbers.map((number) => (
            <S.PageNumber
              key={number}
              $isActive={number === currentPage}
              onClick={() => handlePageChange(number)}
            >
              {number}
            </S.PageNumber>
          ))}
          {startPage + maxPageNumbersToShow <= totalPages && (
            <S.PageButton onClick={handleNextGroup}>{">"}</S.PageButton>
          )}
        </S.Pagination>
      )}
    </>
  );
};

export default List;
