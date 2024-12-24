import GlobalStyle from './style/globalStyle';
import { Outlet, useLocation } from "react-router-dom";
import styled from "styled-components";
import Nav from './layouts/nav/Nav';
import Footer from './layouts/footer/Footer';
import useMediaQueries from "./hooks/useMediaQueries";

const BackGroundColor = styled.div`
  width: 100vw;
  min-height: 100vh;
  flex-direction: column;
  display: flex;
  justify-content: start;
  align-items: start;
  margin: 0 auto;
  background-color: #fff;
  overflow: hidden;
`;

const Wrapper = styled.div`
  min-height: 100%;
  width: 100%;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  padding: ${(props) => 
    props.isExcludedPage ? "0 0" : (props.isDesktop ? "0 250px" : "0 48px")};
`;

const Layout = () => {
  const location = useLocation();
  const { isDesktop } = useMediaQueries(); // Check if it's a desktop screen

  // Nav와 Footer를 제외할 경로들 (로그인, 회원가입)
  const excludedPaths = ["/login", "/signup"];
  
  // Nav만 제외할 경로들 (상세보기, 수정, 작성, 채팅 등)
  const navExcludedPaths = ["/", "/mypage", "/applylist"];

  const isExcludedPage = excludedPaths.includes(location.pathname); // 로그인, 회원가입 경로일 경우
  const isNavExcludedPage = navExcludedPaths.includes(location.pathname); // nav만 제외할 경로들

  return (
    <BackGroundColor>
      {/* 로그인, 회원가입 경로에서만 Nav와 Footer를 숨김 */}
      {!isExcludedPage && <Nav />}
      <Wrapper isExcludedPage={isExcludedPage} isDesktop={isDesktop}>
        <Outlet />
      </Wrapper>
      {/* /login, /signup을 제외한 나머지 페이지에서 Footer를 보임 */}
      {!isExcludedPage && isNavExcludedPage && <Footer />}
    </BackGroundColor>
  );
};

function App() {
  return (
    <>
      <GlobalStyle />
      <Layout />
    </>
  );
}

export default App;
