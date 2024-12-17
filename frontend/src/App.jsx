import GlobalStyle from './style/globalStyle';
import { Outlet } from "react-router-dom";
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

  return (
    <BackGroundColor>
      <Nav/>
      <Wrapper>
        <Outlet />
      </Wrapper>
      <Footer />
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
