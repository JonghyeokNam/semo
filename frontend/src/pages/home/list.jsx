import React, { useState, useEffect } from "react";
import BoardList from "../../components/ui/board/boardList";
import { useLocation, useNavigate } from "react-router-dom";
import * as S from "./style";  

const List = () => {
  const [boardData, setBoardData] = useState([]);
  const itemsPerPage = 5;

  const navigate = useNavigate(); // 페이지 이동을 위한 useNavigate
  const location = useLocation(); // 현재 URL 정보 가져오기

  // URL에서 page 값 가져오기, 없으면 1로 설정
  const query = new URLSearchParams(location.search);
  const pageFromUrl = parseInt(query.get("page"), 10) || 1;

  const [currentPage, setCurrentPage] = useState(pageFromUrl); // 페이지 상태 관리

  const totalPages = Math.ceil(boardData.length / itemsPerPage); // 총 페이지 수 계산


  // 현재 페이지 아이템 계산
  const getCurrentPageItems = () => {
    const startIndex = (currentPage - 1) * itemsPerPage;
    return boardData.slice(startIndex, startIndex + itemsPerPage);
  };

  const currentItems = getCurrentPageItems();

  // 페이지 변경 시 호출
  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
    // URL에 페이지 번호 업데이트
    navigate(`?page=${pageNumber}`);
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  // 페이지 번호 리스트 생성
  const pageNumbers = [];
  for (let i = 1; i <= totalPages; i++) {
    pageNumbers.push(i);
  }

  // URL에 맞춰 currentPage 상태 업데이트
  useEffect(() => {
    setCurrentPage(pageFromUrl);
  }, [pageFromUrl]);

  useEffect(() => {
    // 예시 데이터
    const exampleData = [
      {
        boardId: 1,
        title: "프론트엔드 개발자 모집",
        content: "React를 활용한 웹 애플리케이션 개발",
        hit: 123,
        comments: 4,
        recruitmentType: "전체",
        recruitmentCount: 3,
        recruitmentField: "프론트엔드",
        applicants: {
          frontend: 1,
          backend: 2,
          uiux: 1,
          marketer: 0,
        },
        recruitmentMethod: "원격",
        recruitmentDeadline: "2024-12-31T23:59:59",
        progressPeriod: "3개월",
        createdAt: "11시간 전",
        updatedAt: "2024-12-17T14:00:00",
        author: {
          userId: 101,
          username: "John Doe",
        },
      },
      {
        boardId: 2,
        title: "UI/UX 디자이너 모집",
        content: "앱 인터페이스 디자인과 사용성 테스트",
        hit: 85,
        comments: 2,
        recruitmentType: "프로젝트",
        recruitmentCount: 2,
        recruitmentField: "디자인",
        applicants: {
          frontend: 0,
          backend: 0,
          uiux: 2,
          marketer: 1,
        },
        recruitmentMethod: "오프라인",
        recruitmentDeadline: "2024-12-20T18:00:00",
        progressPeriod: "6개월",
        createdAt: "1시간 전",
        updatedAt: null,
        author: {
          userId: 102,
          username: "Jane Smith",
        },
      },
      {
        boardId: 3,
        title: "UI/UX 디자이너 모집",
        content: "앱 인터페이스 디자인과 사용성 테스트",
        hit: 85,
        comments: 2,
        recruitmentType: "프로젝트",
        recruitmentCount: 2,
        recruitmentField: "디자인",
        applicants: {
          frontend: 0,
          backend: 0,
          uiux: 2,
          marketer: 1,
        },
        recruitmentMethod: "오프라인",
        recruitmentDeadline: "2024-12-20T18:00:00",
        progressPeriod: "6개월",
        createdAt: "1시간 전",
        updatedAt: null,
        author: {
          userId: 102,
          username: "Jane Smith",
        },
      },
      {
        boardId: 4,
        title: "프론트엔드 개발자 모집",
        content: "React를 활용한 웹 애플리케이션 개발",
        hit: 123,
        comments: 4,
        recruitmentType: "스터디",
        recruitmentCount: 3,
        recruitmentField: "프론트엔드",
        applicants: {
          frontend: 1,
          backend: 2,
          uiux: 1,
          marketer: 0,
        },
        recruitmentMethod: "원격",
        recruitmentDeadline: "2024-12-31T23:59:59",
        progressPeriod: "3개월",
        createdAt: "11시간 전",
        updatedAt: "2024-12-17T14:00:00",
        author: {
          userId: 101,
          username: "John Doe",
        },
      },
      {
        boardId: 5,
        title: "UI/UX 디자이너 모집",
        content: "앱 인터페이스 디자인과 사용성 테스트",
        hit: 85,
        comments: 2,
        recruitmentType: "프로젝트",
        recruitmentCount: 2,
        recruitmentField: "디자인",
        applicants: {
          frontend: 0,
          backend: 0,
          uiux: 2,
          marketer: 1,
        },
        recruitmentMethod: "오프라인",
        recruitmentDeadline: "2024-12-20T18:00:00",
        progressPeriod: "6개월",
        createdAt: "1시간 전",
        updatedAt: null,
        author: {
          userId: 102,
          username: "Jane Smith",
        },
      },
      {
        boardId: 6,
        title: "UI/UX 디자이너 모집",
        content: "앱 인터페이스 디자인과 사용성 테스트",
        hit: 85,
        comments: 2,
        recruitmentType: "스터디",
        recruitmentCount: 2,
        recruitmentField: "디자인",
        applicants: {
          frontend: 0,
          backend: 0,
          uiux: 2,
          marketer: 1,
        },
        recruitmentMethod: "오프라인",
        recruitmentDeadline: "2024-12-20T18:00:00",
        progressPeriod: "6개월",
        createdAt: "1시간 전",
        updatedAt: null,
        author: {
          userId: 102,
          username: "Jane Smith",
        },
      },
    ];

    setBoardData(exampleData);
  },  []);

  return (
    <>
       {currentItems.map((board) => ( 
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
