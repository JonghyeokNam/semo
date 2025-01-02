import React, { useState } from "react";
import * as S from "./style";
import { BiMessageRoundedDots } from "react-icons/bi";
import { Link } from "react-router-dom"; 
import useMediaQueries from "../../hooks/useMediaQueries";
import Notification from "../../components/ui/notification/notificationRead";
import { useCheckNoReadNotificationStore, useGetNotificationsStore } from "../../store/useNotificationStore";

const Nav = () => {
  const {isDesktop } = useMediaQueries();
  const [open, setOpen] = useState(false);
  const { fetchList } = useGetNotificationsStore();
  const { isReadAll } = useCheckNoReadNotificationStore();

  const openNotification = (e) => {
    e.stopPropagation(); // 이벤트 전파 중단
    e.preventDefault(); // 기본 동작 방지
    fetchList();
    setOpen(true);
  };

  const closeModal = () => {
    setOpen(false); // 모달 닫기
  };

  return (
    <>
      <S.NavWrapper $isDesktop={isDesktop}>
        <S.Text>이유진님, 환영합니다.</S.Text>
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
            <S.StyledGoBell size={36}  onClick={openNotification}/>
            {isReadAll && <S.RedDot />}
        </S.RightContainer>
      </S.Nav2Wrapper>
      <Notification isOpen={open} onClose={closeModal}/>
    </>
  );
};

export default Nav;
