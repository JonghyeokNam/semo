import styled from "styled-components";

export const mypageWrapper = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;

export const MainTitle = styled.h1`
    margin: 45px 0px;
    font-size: 27px;
    font-weight: bold;
`;

export const Container = styled.div`
  padding: ${(props) => (props.$isMobile ? "62px 50px" : "62px 80px")};
  box-shadow: 0px 4px 48px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  background-color: #ffffff;
  margin-bottom: 45px;
  max-width: 1000px;
  width: 100%;
`;

export const Title = styled.h1`
  font-size: 25px;
  font-weight: bold;
  margin: 0;
  color: var(--green);

`;

export const Content = styled.div`
  font-size: 16px;
  color: #333;
`;

export const NoData = styled.div`
  font-size: 20px;
  font-weight: bold;
  color: #212529;
  display:flex;
  justify-content:center;
  align-items:center;
  height: 100px;
`;

export const Row = styled.div`
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  width: 100%;
  flex-wrap: wrap;
  gap: 20px;
`;

export const Column = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

export const ProfileImage = styled.img`
  width: 100px;
  height: auto;
  margin-bottom: 16px;
`;

export const WelcomeText = styled.p`
  font-size: 18px;
  font-weight: bold;
  color: #212529;
`;

// Box 스타일: 소속 캠퍼스와 교육과정
export const Box = styled.div`
  padding: 10px 15px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #fff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  width: 310px;
  margin-bottom: 20px;
`;

export const BoxColumn = styled.div`

`

export const BoxTitle = styled.h3`
  font-size: 16px;
  font-weight: bold;
  color: #212529;
  margin-bottom: 8px;
`;

export const BoxContent = styled.p`
  font-size: 14px;
  color: #333;
`;

// 프로필 저장 버튼 스타일
export const SaveButton = styled.button`
  padding: 10px 20px;
  background-color: var(--green);
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin-top: 10px;
  font-weight: bold;
  width: 310px;
  
  &:hover {
    background-color: #2d8f44;
  }
`;

export const ContainerBody = styled.div`
  max-height: 20rem;
  overflow: auto;
`