import styled from "styled-components";

// 모달 오버레이 스타일
export const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
`;

// 모달 컨텐츠 스타일
export const ModalContent = styled.div`
  background: white;
  border-radius: 8px;
  box-shadow: inset;
  padding: 45px 100px;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  min-width: 400px;
  max-width: 700px;
`;

// 모달 닫기 버튼 스타일
export const CloseButton = styled.button`
  position: absolute;
  font-size: 50px;
  right: 30px;
  top: 30px;
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
`;

export const Title = styled.h1`
  font-size: 30px;
  font-weight: bold;
  margin-bottom: 40px;
  text-align: center;
`;

export const Column = styled.h1`
  flex-direction: column;
  display: flex;
  justify-content: start;
  width: 100%;
`;

export const ContentInput = styled.textarea`
  width: 100%;
  min-height: 200px;
  border: 1px solid #cccccc;
  border-radius: 4px;
  padding: 10px;
  font-size: 14px;
  color: #333;
  resize: none;

  outline-color:  #495057;

`;

export const Input = styled.input`
  padding: 12px;
  font-size: 14px;
  border: 1px solid #D9D9D9;
  border-radius: 10px;
  width: 270px;
  height: 40px;
  outline: none;
  transition: border-color 0.2s;

  &:focus {
    border-color: #495057;
  }

  &::placeholder {
    color: #999999;
  }
`;

export const ContentTitle = styled.h2`
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
  text-align: left;
  color: #333;
`;

export const ContentDescription = styled.p`
  font-size: 11px;
  margin-bottom: 10px;
  color: #333;
`;

// 완료 버튼 Wrapper
export const CompleteButtonWrapper = styled.div`
  width: 100%;
  margin-top: 20px;
  display: flex; 
  justify-content: center; 
`;

// 완료 버튼 스타일
export const CompleteButton = styled.button`
  background-color: var(--green);
  color: white;
  width: 270px;
  height: 40px;
  font-size: 16px;
  font-weight: 700;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  transition: background-color 0.2s;

  &:hover {
    background-color: #44A253;
  }
`;