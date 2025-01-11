import React from "react";
import * as S from "./style";
import { useNavigate } from 'react-router-dom';

// 이유진
// 뒤로가기 버튼
export const Back = () => {
    const navigate = useNavigate();

    const handleBackClick = () => {
        navigate(-1);
    };

  return (
    <S.BackButton  onClick={handleBackClick}>
      <S.BackIcon />
    </S.BackButton>
  );
};