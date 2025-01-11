import React from "react";
import * as S from "./style";
import useMediaQueries from "../../hooks/useMediaQueries";

// 이유진
const Container = ({ title, children, type }) => {
    const { isMobile } = useMediaQueries();

  return (
    <S.Container $isMobile={isMobile}>
      <S.Title>{title}</S.Title>
      {children ? (
        <S.Content>{children}</S.Content>
      ) : (
        <S.NoData>{`${type}한 글이 없습니다.`}</S.NoData>
      )}
    </S.Container>
  );
};

export default Container;
