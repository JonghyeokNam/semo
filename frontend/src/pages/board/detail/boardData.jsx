import React from "react";
import * as S from "./style";

// BoardData 컴포넌트
export const BoardData = () => {
    return (
      <S.BoardData>
        <S.RowGroup>
          <S.DataType>모집구분</S.DataType>
          <S.DataItem>프로젝트</S.DataItem>
        </S.RowGroup>
        <S.RowGroup>
          <S.DataType>진행 방식</S.DataType>
          <S.DataItem>온/오프라인</S.DataItem>
        </S.RowGroup>
        <S.RowGroup>
          <S.DataType>모집 인원</S.DataType>
          <S.DataItem>3명</S.DataItem>
        </S.RowGroup>
        <S.RowGroup>
          <S.DataType>모집 마감</S.DataType>
          <S.DataItem>2024.10.13</S.DataItem>
        </S.RowGroup>
        <S.RowGroup>
          <S.DataType>모집 분야</S.DataType>
          <S.DataItem>백엔드 프론트엔드</S.DataItem>
        </S.RowGroup>
        <S.RowGroup>
          <S.DataType>진행 기간</S.DataType>
          <S.DataItem>3개월</S.DataItem>
        </S.RowGroup>
      </S.BoardData>
    );
  };