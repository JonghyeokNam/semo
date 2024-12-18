import styled from "styled-components";

export const SelectBox = styled.div`
  position: relative;
  width: 300px;
  height: 40px;
  padding: 10px;
  border-radius: 10px;
  background-color: #ffffff;
  align-self: center;
  border: 1px solid #D9D9D9;
  cursor: pointer;

  &::before {
    content: "⌵";
    position: absolute;
    top: 4px;
    right: 8px;
    color: #777777;
    font-size: 20px;
    font-weight: bold;
  }
`;

export const Label = styled.label`
  font-size: 14px;
  display: inline-block;
`;

export const SelectOptions = styled.ul`
  position: absolute;
  top: 38px;
  left: 0;
  width: 100%;
  overflow-y: auto;  
  border: ${(props) => (props.show ? "1px solid #D9D9D9;" : "none")};
  border-radius: 10px;
  max-height: ${(props) => (props.show ? "200px" : "0")};
  background-color: #fefefe;
  z-index: 1000;

  /* 스크롤바 스타일 */
  ::-webkit-scrollbar {
    width: 4px;
  }
  ::-webkit-scrollbar-thumb {
    background-color: var(--green);
    border-radius: 10px;
  }
  ::-webkit-scrollbar-track {
    background-color: #D9D9D9;
    border-radius: 0px 3px 3px 0px;
  }
`;

export const Option = styled.li`
  font-size: 14px;
  padding: 10px;
  transition: background-color 0.2s ease-in;

  &:hover {
    color: white;
    border-radius: 5px;
   background-color: var(--green);
  }
`;
