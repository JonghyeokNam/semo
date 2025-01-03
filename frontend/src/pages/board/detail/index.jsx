import React, { useEffect } from "react";
import * as S from "./style";
import { Back } from "./back";
import { TopBar } from "./topbar";
import { BoardData } from "./boardData";
import { Comment } from "./comment";
import { TopBarApply } from "./topbarApply";
import { useGetBoardDetailStore } from "../../../store/useBoardStore"; // Zustand Store 가져오기
import { useParams } from "react-router-dom"; // URL에서 boardId를 가져오기 위한 훅
import { replaceNewlinesWithBr } from "../../../utils/replaceUtil";

const Index = () => {
  const { boardId } = useParams(); // URL에서 boardId 가져오기
  const { boardInfo, fetchBoardInfo, loading, error } = useGetBoardDetailStore(); // Zustand 상태 및 동작 가져오기

  const title = boardInfo?.title || "제목을 불러오는 중...";
  const content = boardInfo?.content || "내용을 불러오는 중...";

  console.log(replaceNewlinesWithBr(content));

  useEffect(() => {
    if (boardId) {
      fetchBoardInfo(boardId); // boardId로 데이터 Fetch
    }
  }, [boardId, fetchBoardInfo]);

  useEffect(() => {
    window.scrollTo({ top: 0, behavior: "smooth" });
  }, []); 

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <S.DetailContainer>
      <Back/>
      <S.Title>{title}</S.Title>
      <TopBarApply boardInfo={boardInfo}/>
      <S.Separator />
      <BoardData boardInfo={boardInfo} />
      <S.Title2>프로젝트 소개</S.Title2>
      <S.Separator />
      <S.Content>{replaceNewlinesWithBr(content)}</S.Content>
      <Comment/>
    </S.DetailContainer>
  );
};

export default Index;
