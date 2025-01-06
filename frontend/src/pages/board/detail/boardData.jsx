import React from "react";
import * as S from "./style";

// 이유진, 주현우
// BoardData 컴포넌트
export const BoardData = ({boardInfo}) => {

  const recruitmentField = boardInfo?.recruitmentField || "알 수 없음";
  const recruitmentMethod = boardInfo?.recruitmentMethod === "online"
  ? "온라인"
  : boardInfo?.recruitmentMethod === "offline"
  ? "오프라인"
  : boardInfo?.recruitmentMethod === "both"
  ? "온/오프라인"
  : "알 수 없음";
  const recruitmentCount = boardInfo?.recruitmentCount || 0;
  const recruitmentTypes = Array.isArray(boardInfo?.recruitmentTypes)
    ? boardInfo.recruitmentTypes
    : [];
  const recruitmentDeadline = boardInfo?.recruitmentDeadline
    ? new Date(boardInfo.recruitmentDeadline).toLocaleDateString()
    : "알 수 없음";
  const progressPeriod = boardInfo?.progressPeriod || "알 수 없음";

  // 모집 구분(recruitmentTypes) 한글 변경 로직
  const mapRecruitmentTypeToKorean = (type) => {
    const typeMap = {
      frontend: "프론트엔드",
      backend: "백엔드",
      marketer: "마케터",
      designer: "UI/UX",
    };
    return typeMap[type] || type;
  };
  
  const translatedRecruitmentTypes = recruitmentTypes.map(mapRecruitmentTypeToKorean);

    return (
      <S.BoardData>
        <S.RowGroup>
          <S.DataType>모집 구분</S.DataType>
          <S.DataItem>{recruitmentField}</S.DataItem>
        </S.RowGroup>
        <S.RowGroup>
          <S.DataType>진행 방식</S.DataType>
          <S.DataItem>{recruitmentMethod}</S.DataItem>
        </S.RowGroup>
        <S.RowGroup>
          <S.DataType>모집 인원</S.DataType>
          <S.DataItem>{recruitmentCount}명</S.DataItem>
        </S.RowGroup>
        <S.RowGroup>
          <S.DataType>모집 마감</S.DataType>
          <S.DataItem>{recruitmentDeadline}</S.DataItem>
        </S.RowGroup>
        <S.RowGroup>
          <S.DataType>모집 분야</S.DataType>
          <S.DataItem>{translatedRecruitmentTypes.join(" ")}</S.DataItem>
        </S.RowGroup>
        <S.RowGroup>
          <S.DataType>진행 기간</S.DataType>
          <S.DataItem>{progressPeriod}</S.DataItem>
        </S.RowGroup>
      </S.BoardData>
    );
  };