import styled from "styled-components";

export const IndexContainer = styled.div`
  display: flex;
  justify-content: center;
  flex-wrap: wrap; 
  flex-direction: row;
  padding: 20px 0px;
  width: 80%;
  margin: 0 auto; 
`;

export const Index2Container = styled.div`
  display: flex;
  flex-direction: column;
  padding: 10px 0px;
  width: 80%;
  margin: 0 auto; 
`;


export const PaddingRow = styled.div`
  display: flex;
  justify-content: space-between;
  flex-direction: row;
`;

export const FilterRow = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center; 
  margin-bottom: 16px;
  flex-wrap: wrap; 
  width: 100%;
`;

export const Column = styled.div`
  display: flex;
  flex-direction: column;
  align-items: start;
  flex: 1;
  min-width: 200px; /* 각 컬럼의 최소 너비를 설정 */
  margin-bottom: 10px;
`;

export const Center = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center; 
`;

export const ColumnCenter = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const Image = styled.img`
  width: 200px;
  height: auto;
  margin-bottom: 10px;
`;

export const Title = styled.h3`
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 10px;
`;

export const Content = styled.div`
  font-size: 14px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 10px;
  color: var(--green);
`;

export const Content2 = styled.div`
  font-size: 14px;
  font-weight: bold;
  text-align: center;
`;

//rank
export const RankContainer = styled.div`
  background-color: #fff;
  border-radius: 30px;
  padding: 15px 20px;
  border: 2px solid #D1D1D1;
  display: flex;
`;

export const RankTable = styled.table`
  width: 270px;
  border-collapse: collapse;  
  display: block; 
  white-space: nowrap; 
`;

export const TableHeader = styled.th`
  padding: 10px 20px;
  text-align: center;
  font-weight: bold;
  border-bottom: 2px solid #D1D1D1;
`;

export const TableCell = styled.td`
  padding: 8px 20px;
  text-align: center;

  /* 순위가 1일 때 초록색으로 글자 색상 변경 */
  ${(props) =>
    props.rank === 1 &&
    `
    color: var(--green);
    font-weight: bold;
  `}

  &:first-child {
    border-left: none;
  }

  &:last-child {
    border-right: none;
  }
`;

export const Button = styled.button`
  padding: 10px 20px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;

  &:hover {
    background-color: #45a049;
  }
`;

export const FilterItem = styled.div`
  padding: 10px 20px;
  cursor: pointer;
  font-size: 20px;
  font-weight: bold;
  color: ${({ isSelected }) => (isSelected ? "#444444" : "#868E96")};
  // border-bottom: ${({ isSelected }) =>
    isSelected ? "2px solid #444444" : "none"};
`;

export const TagBox = styled.button`
  border: 1px solid #e3e3e3;
  background-color: #ffffff;
  padding:  ${(props) => (props.$isDesktop ? '5px 12px' : '3px 8px')};
  border-radius: 38px;
  font-size: ${(props) => (props.$isDesktop ? '12px' : '10px')};
  color: #444444;
  cursor: pointer;
  margin-right: 10px;
  height: 38px;
  width: ${(props) => (props.$isDesktop ? '120px' : '80px')}

  &:hover {
    background-color: #f5f5f5;
  }

  &:active,
  &.selected {
    background-color: #fff;
    border-color: #4eb95f;
    color: #4eb95f;
  }
`;

export const SearchContainer = styled.div`
  display: flex;
  align-items: center;
  background-color: #f5f5f5;
  border-radius: 38px;
  padding: 6px 12px;
  width: 100%;
  max-width: 200px;
  height: 38px;
`;

export const SearchIcon = styled.span`
  margin-right: 8px;
  color: #757575;
  font-size: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const SearchInput = styled.input`
  border: none;
  background: none;
  outline: none;
  color: #757575;
  font-size: 12px;
  flex: 1; 
  padding: 0;

  &::placeholder {
    color: #757575;
  }
`;

export const RegisterButton = styled.button`
  background-color: var(--green);
  color: white;
  border: none;
  border-radius: 36px;
  cursor: pointer;
  height: 38px;
  font-weight: bold;
  font-size: ${(props) => (props.$isDesktop ? '14px' : '10px')};
  width: ${(props) => (props.$isDesktop ? '120px' : '100px')}; 
  &:hover {
    background-color: #3D8D4A;
  }
`;

//페이지네이션
export const Pagination = styled.div`
  display: flex;
  justify-content: center;
  margin: 24px 0;
`;

export const PageNumber = styled.span`
  margin: 0 4px;
  cursor: pointer;
  padding: 10px 15px;
  border: 1px solid #ddd;
  font-family: "MonRegular";
  font-size: 16px;
  background-color: ${(props) => (props.$isActive ? "#e7ebef" : "white")};
  color: black;
  border-radius: 10px;
  border: none;

  &:hover {
    background-color: #e7ebef;
  }
`;