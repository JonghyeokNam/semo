import React from "react";
import * as S from "./style";
import { BiMessageRoundedDots } from "react-icons/bi";
import { GoBell } from "react-icons/go";
import { Link } from "react-router-dom"; // Link import

const Nav = () => {
  return (
    <>
      <S.NavWrapper>
        <S.Text>이유진님, 환영합니다.</S.Text>
        <S.Divider />
        <S.StyledLink to="/mypage">
          <S.Text>마이페이지</S.Text>
        </S.StyledLink>
        <S.Divider />
        <S.Text>로그아웃</S.Text>
      </S.NavWrapper>

      <S.Nav2Wrapper>
        <S.LeftContainer>
          <S.Logo>
            <img src="/img/logo.png" alt="Logo" />
          </S.Logo>
          <S.LogoText>SeMO</S.LogoText>
        </S.LeftContainer>

        <S.RightContainer>
          <Link to="/chat">
            <BiMessageRoundedDots size={36} />
          </Link>
          <GoBell size={36} />
        </S.RightContainer>
      </S.Nav2Wrapper>
    </>
  );
};

export default Nav;
