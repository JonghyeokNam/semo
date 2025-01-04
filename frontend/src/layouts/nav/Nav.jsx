import React, { useState, useEffect } from "react";
import * as S from "./style";
import { BiMessageRoundedDots } from "react-icons/bi";
import { Link, useNavigate } from "react-router-dom"; 
import useMediaQueries from "../../hooks/useMediaQueries";
import Notification from "../../components/ui/notification/notificationRead";
import { useCheckNoReadNotificationStore, useGetNotificationsStore } from "../../store/useNotificationStore";
import { useAuthStore } from "../../store/useAuthStore";

const Nav = () => {
  const {isDesktop } = useMediaQueries();
  const [open, setOpen] = useState(false);
  const { fetchList } = useGetNotificationsStore();
  const { isReadAll } = useCheckNoReadNotificationStore();
  const { isLoggedIn, user, fetchUserInfo, storeLogout } = useAuthStore();
  const navigate = useNavigate();

  const openNotification = (e) => {
    e.stopPropagation(); // 이벤트 전파 중단
    e.preventDefault(); // 기본 동작 방지
    fetchList();
    setOpen(true);
  };

  const closeModal = () => {
    setOpen(false); // 모달 닫기
  };

  const handleLogout = () => {
    storeLogout(); // 로그아웃 처리
    navigate("/login"); // /login 경로로 이동
  };

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
        <S.Text onClick={handleLogout}>로그아웃</S.Text>
      </S.NavWrapper>

      <S.Nav2Wrapper $isDesktop={isDesktop}>
        <Link to="/home">
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
            <S.NotificationContainer>
              <S.StyledGoBell size={36}  onClick={openNotification}/>
              {isReadAll && <S.RedDot />}
            </S.NotificationContainer>
        </S.RightContainer>
      </S.Nav2Wrapper>
      <Notification isOpen={open} onClose={closeModal}/>
    </>
  );
};

export default Nav;
