import React, { useEffect } from "react";
import * as S from "./style";
import { Back } from "./back";
import { TopBar } from "./topbar";
import { BoardData } from "./boardData";
import { Comment } from "./comment";
import { TopBarApply } from "./topbarApply";

const Index = () => {

  useEffect(() => {
    // 컴포넌트가 마운트될 때 화면 상단으로 스크롤
    window.scrollTo({ top: 0, behavior: "smooth" });
  }, []); // 빈 배열을 넣어서 첫 렌더링 시 한 번만 실행되도록 설정
  
  return (
    <S.DetailContainer>
      <Back/>
      <S.Title>프론트엔드 모집</S.Title>
      <TopBarApply/>
      <S.Separator />
      <BoardData/>
      <S.Title2>프로젝트 소개</S.Title2>
      <S.Separator />
      <S.Content>프로젝트 소개 내용이 여기에 들어갑니다.</S.Content>
      <Comment/>
    </S.DetailContainer>
  );
};

export default Index;
