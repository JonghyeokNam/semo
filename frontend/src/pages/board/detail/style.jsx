import styled from "styled-components";
import { FaArrowLeft } from "react-icons/fa";

// 스타일 컴포넌트 (styled-components)

export const DetailContainer = styled.div`
  padding: 0px 30px;
`;

export const BackButton = styled.button`
  padding: 10px 0px;
  background: none;
  border: none;
  font-size: 16px;
  cursor: pointer;
  color: #808080;
  display: flex;
  align-items: center;
`;

export const BackIcon = styled(FaArrowLeft)`
  font-size: 25px;
`;

export const TopBar = styled.div`
  display: flex;
  flex-direction: Row;
  justify-content: space-between;
`;

export const Row = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 0;
  gap: 20px;
`;

export const RowGroup = styled.div`
  display: flex;
  justify-content: flex-start;  // 항목들을 왼쪽으로 정렬
  gap: 30px;  // 항목들 사이 간격을 10px로 줄이기
`;

export const UserInfo = styled.div`
  display: flex;
  align-items: center;
`;

export const ProfileImage = styled.img`
  width: 100%;  
  height: 100%; 
  object-fit: cover;  
`;

export const ImgContainer = styled.div`
  border-radius: 50%;
  width: 30px;
  height: 30px;
  border: 1px #F2F2F2 solid;
  overflow: hidden;
`;

export const UserName = styled.span`
  font-size: 16px;
  margin-left: 10px;
`;

export const VerticalLine = styled.div`
  width: 2px;
  height: 20px;
  background-color: #ccc;
`;

export const Date = styled.span`
  font-size: 14px;
  color: #888;
`;

export const ViewCountWrapper = styled.div`
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #888;

  & > svg {
    margin-right: 5px;
  }
`;

export const Status = styled.span`
  font-size: 14px;
  color: #333333;
  cursor: pointer;

  &:hover {
        text-decoration: underline;
    }
`;

export const BoardData = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  margin: 40px 0;
  gap: 20px;
`;

export const DataItem = styled.div`
  font-size: 14px;
  color: #333;
  font-weight: bold;
`;

export const DataType = styled.div`
  font-size: 14px;
  color: #717171;
  font-weight: bold;
`;

export const Separator = styled.div`
  border: none;
  border-top: 1px solid #F2F2F2;
`;

export const Title = styled.h1`
  font-size: 24px;
  margin: 20px 0px;
  color: #333;
  font-weight: bold;
`;

export const Title2 = styled.h2`
  font-size: 20px;
  margin: 10px 0px;
  font-weight: bold;
  color: #333;
`;

export const Content = styled.div`
  font-size: 14px;
  color: #333;
  margin-top: 10px;
`;