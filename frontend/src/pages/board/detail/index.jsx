import React from "react";
import * as S from "./style";
import { Back } from "./back";
import { TopBar } from "./topbar";
import { BoardData } from "./boardData";
import { Comment } from "./comment";
import { TopBarApply } from "./topbarApply";

const index = () => {
  
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

export default index;
