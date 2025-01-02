import React, { useEffect } from "react";
import * as S from "./style";
import { BiMessageRoundedDots } from "react-icons/bi";
import { GoBell } from "react-icons/go";
import { Link } from "react-router-dom"; 
import useMediaQueries from "../../hooks/useMediaQueries";
import { useAuthStore } from "../../store/useAuthStore";

const Nav = () => {
  const {isDesktop } = useMediaQueries();
  const { isLoggedIn, user, fetchUserInfo } = useAuthStore();

  useEffect(() => {
    if (isLoggedIn) {
      fetchUserInfo(); // 사용자 정보 요청
    }
  }, [isLoggedIn, fetchUserInfo]);

  return (
    <>
      <S.NavWrapper $isDesktop={isDesktop}>
        <S.Text>{user ? `${user.username}님, 환영합니다.` : '로그인 해주세요.'}</S.Text>
        <S.Divider />
        <S.StyledLink to="/mypage">
          <S.Text>마이페이지</S.Text>
        </S.StyledLink>
        <S.Divider />
        <S.Text>로그아웃</S.Text>
      </S.NavWrapper>

      <S.Nav2Wrapper $isDesktop={isDesktop}>
        <Link to="/">
          <S.LeftContainer>
            <S.Logo>
              <img src="/img/logo.png" alt="Logo" />
            </S.Logo>
            <S.LogoText>SeMO</S.LogoText>
          </S.LeftContainer>
        </Link>

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
