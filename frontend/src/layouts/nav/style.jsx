import { Link } from "react-router-dom";
import styled from "styled-components";

export const NavWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-end; /* 오른쪽 정렬 */
  width: 100%; /* 100% 너비로 전체 영역을 채움 */
  height: 45px;
  padding: ${(props) => (props.$isDesktop ? "0 250px" : "0 32px")};
  font-size: ${(props) => (props.$isDesktop ? "14px" : "10px")};
  
`;

export const Nav2Wrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between; /* 양쪽 끝에 정렬 */
  width: 100%;
  height: 75px;
  padding: ${(props) => (props.$isDesktop ? "0 250px" : "0 48px")};
`;

export const LeftContainer = styled.div`
  display: flex;
  align-items: center;
`;

export const RightContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
`;

export const Logo = styled.div`
  img {
    width: 38px;
    height: auto;
    margin-right: 5px;
  }
`;

export const LogoText = styled.span`
  font-size: 30px;
  color: var(--green);
  font-weight: 700;
`;

export const Text = styled.span`
  color: #493f3c;
  margin: 0 15px;
`;

export const Divider = styled.div`
  width: 3px;
  height: 22px;
  background-color: #c7a671;
`;

/* Link 내부 스타일 초기화 */
export const StyledLink = styled(Link)`
  text-decoration: none; /* 밑줄 제거 */
  color: inherit; /* 부모 색상 상속 */
  display: flex;
  align-items: center;
`;
