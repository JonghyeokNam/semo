import styled from "styled-components";

export const ChatWrapper = styled.div`
  display: flex;
  justify-content: center;
  height: 100vh; 
  background-color: ${(props) => (props.$isMobile ? "pink" : "#f8f9fa")};
`;

export const Row = styled.div`
  display: flex;
  flex-direction: ${(props) => (props.$isMobile ? "column" : "row")}; 
  gap: ${(props) => (props.$isMobile ? "30px" : "20px")}; 
  margin-top: ${(props) => (props.$isMobile ? "20px" : "80px")};
`;

// 채팅방 목록
export const ListContainer = styled.div`
  box-shadow: 0px 4px 48px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  background-color: #ffffff;
  width: ${(props) => (props.$isMobile ? "300px" : "400px")}; 
  height: ${(props) => (props.$isMobile ? "400px" : "550px")}; 
`;

export const Title = styled.div`
  font-size: ${(props) => (props.$isMobile ? "16px" : "20px")};
  font-weight: bold;
  padding: 20px;
  border-bottom: 1px solid #DEE2E6;
`;

// 채팅방 대화
export const RoomContainer = styled.div`
  box-shadow: 0px 4px 48px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  background-color: #ffffff;
  width: ${(props) => (props.$isMobile ? "300px" : "400px")}; 
  height: ${(props) => (props.$isMobile ? "400px" : "550px")}; 
`;

// 채팅방 대화 없을 때
export const NoChatSelected = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  font-size: 1.2rem;
  font-weight: 600;
  color: #212529; 
  height: 100%; /* 부모 요소의 높이에 맞춤 */
`;


export const MessageInputContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-top: 1px solid #ddd;
  background-color: pink;
  position: absolute;
  bottom: -20px; /* Position it at the bottom */
  left: 0;
  width: 100%; 
  z-index: 10; 
`;

export const MessageInput = styled.textarea`
  width: 80%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  resize: none;
`;

export const SendButton = styled.button`
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  
  &:hover {
    background-color: #0056b3;
  }
`;
