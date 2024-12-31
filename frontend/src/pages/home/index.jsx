import React, { useState } from "react";
import List from "./list";
import { Link } from "react-router-dom"; 
import * as S from "./style";  
import { FaSearch } from "react-icons/fa";
import useMediaQueries from "../../hooks/useMediaQueries";
import ActRank from "./ActRank";
import RecruitRank from "./recruitRank";


const Index = () => {
  const {isDesktop } = useMediaQueries();

  const [selectedType, setSelectedType] = useState("전체"); // 기본값: 전체

  const handleSelect = (type) => {
    setSelectedType(type);
  };

  const [isTagSelected, setIsTagSelected] = useState(false);

  const handleTagClick = () => {
    setIsTagSelected(!isTagSelected);
  };

  return (
    <>

      <S.IndexContainer $isDesktop={isDesktop}>
        <S.Column>
          <S.Title>🔥 모집이 활발한 캠퍼스</S.Title>
          <RecruitRank />
        </S.Column>
        <S.Column>
          <S.Title>❤️‍🔥 참여가 활발한 캠퍼스</S.Title>
          <ActRank />
        </S.Column>
        <S.ColumnCenter>
          <S.Image src="/img/sesacCheer.png" alt="새싹사진" />
          <S.Content>모임이 활발한 캠퍼스 TOP!</S.Content>
          <S.Content2>당신도 열정의 주인공이 될 수 있습니다!</S.Content2>
        </S.ColumnCenter>
      </S.IndexContainer>

    <S.Index2Container>
        <S.FilterRow>
          <S.FilterItem
            $isSelected={selectedType === "전체"}
            onClick={() => handleSelect("전체")}
          >전체</S.FilterItem>

          <S.FilterItem
            $isSelected={selectedType === "프로젝트"}
            onClick={() => handleSelect("프로젝트")}
          >프로젝트</S.FilterItem>

          <S.FilterItem
            $isSelected={selectedType === "스터디"}
            onClick={() => handleSelect("스터디")}
          >스터디</S.FilterItem>
        </S.FilterRow>
      <S.PaddingRow>
        <S.FilterRow>
        <S.TagBox $isDesktop={isDesktop}
          className={isTagSelected ? "selected" : ""}
          onClick={handleTagClick}
        >
          👀 모집 중만 보기
        </S.TagBox>
        <S.SearchContainer>
          <S.SearchIcon>
            <FaSearch />
          </S.SearchIcon>
          <S.SearchInput placeholder="제목, 글 내용을 검색해보세요." />
        </S.SearchContainer>
        </S.FilterRow>
        <Link to="/board/write">
        <S.RegisterButton $isDesktop={isDesktop}>팀원 모집하기</S.RegisterButton>
        </Link>
      </S.PaddingRow>
      </S.Index2Container>
    <S.Center>
    <List/>
      
    </S.Center>
    
    </>
  );
};

export default Index;
