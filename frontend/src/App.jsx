import GlobalStyle from './style/globalStyle';
import { Outlet, useLocation } from "react-router-dom";
import styled from "styled-components";
import Nav from './layouts/nav/Nav';
import Footer from './layouts/footer/Footer';

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
  align-items: center;
`;

const Layout = () => {
  const location = useLocation();

  // Nav와 Footer를 제외할 경로들
  const excludedPaths = ["/login", "/signup", "/otherpage"];
  const isExcludedPage = excludedPaths.includes(location.pathname);

  return (
    <BackGroundColor>
      {!isExcludedPage && <Nav />}
      <Wrapper>
        <Outlet />
      </Wrapper>
      {!isExcludedPage && <Footer />}
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
