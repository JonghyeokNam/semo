import React, { useEffect } from "react";
import * as S from "./style";
import Container from "../mypage/container";
import BoardListApplyList from "../../components/ui/board/boardListApplyList";
import ApplyComponent from "./applyComponent";

const Index = () => {

  useEffect(() => {
    // 컴포넌트가 마운트될 때 화면 상단으로 스크롤
    window.scrollTo({ top: 0, behavior: "smooth" });
  }, []); // 빈 배열을 넣어서 첫 렌더링 시 한 번만 실행되도록 설정
  
  
  return (
    <S.applyWrapper>
      <S.MainTitle>지원 리스트</S.MainTitle>
      <Container title="작성글" type="작성">
        <BoardListApplyList/>
      </Container>
      <Container title="지원자" type="지원" >
        <ApplyComponent/>
        <ApplyComponent/>
      </Container>
    </S.applyWrapper>
  );
};

export default Index;
