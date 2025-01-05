import React, { useEffect } from "react";
import * as S from "./style";
import Container from "../mypage/container";
import BoardListApplyList from "../../components/ui/board/boardListApplyList";
import ApplyComponent from "./applyComponent";
import { useLocation } from "react-router-dom";
import { useGetBoardApplyFormStore } from "../../store/useApplyStore";

const Index = () => {
  const { boardApplyForms, fetchBoardApplyForms } = useGetBoardApplyFormStore();

  const location = useLocation();
  const { boardData } = location.state || {};

  useEffect(() => {
    // 컴포넌트가 마운트될 때 화면 상단으로 스크롤
    window.scrollTo({ top: 0, behavior: "smooth" });
    fetchBoardApplyForms(boardData.boardId);
  }, [fetchBoardApplyForms]); // 빈 배열을 넣어서 첫 렌더링 시 한 번만 실행되도록 설정
  
  return (
    <S.applyWrapper>
      <S.MainTitle>지원 리스트</S.MainTitle>
      <Container title="작성글" type="작성">
        <BoardListApplyList boardData={boardData}/>
      </Container>
      <Container title="지원자" type="지원" >
        {boardApplyForms.map((item, index) => (
          <ApplyComponent formData={item} key={index}/>
        ))}
      </Container>
    </S.applyWrapper>
  );
};

export default Index;