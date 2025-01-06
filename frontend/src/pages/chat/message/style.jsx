import styled from "styled-components";

// 이유진, 남종혁
export const ChatRoomContainer = styled.div`
  display: flex;
  flex-direction: column;
  position: relative;
`;

export const ChatTitle = styled.div`
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
`;

export const MessagesContainer = styled.div`
  box-shadow: 0px 4px 48px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  background-color: #ffffff;
  width: ${(props) => (props.$isMobile ? "300px" : "400px")}; 
  height: ${(props) => (props.$isMobile ? "400px" : "477px")}; 
  display: flex;
  flex-direction: column;
  overflow-y: scroll;
    &::-webkit-scrollbar {
        display: none;
    }
  padding: 20px;
`;

export const ContentContainer = styled.div`
  display: flex;
  flex-direction: ${(props) => (props.isUser ? "row-reverse" : "row")}; /* 사용자 메시지는 오른쪽으로, 상대방은 왼쪽으로 배치 */
  align-items: flex-start;
  margin-bottom: 10px;
  gap: 10px;
`;

export const UserName = styled.div`
  display: flex;
  flex-direction: ${(props) => (props.isUser ? "row-reverse" : "row")};
  font-weight: bold;
  font-size: 14px;
  margin-bottom: 5px; 
`;

export const Content = styled.div`
  font-size: 14px;
  background-color: #F1F1F1;
  border-radius: 15px;
  padding: 10px 15px;
  word-wrap: break-word;
  display: inline-block; 
  position: relative;
  z-index: 0;
`;

export const Message = styled.div``;

export const Time = styled.div`
  font-size: 12px;
  color: #777;
  margin-top: 5px;
  align-self: flex-end; 
`;

// 왼쪽 말풍선 꼬리
export const Tail = styled.div`
  border-top: 15px solid #f1f1f1;
  border-left: 15px solid transparent;
  content: "";
  position: absolute;
  left: -12px;  
  top: 12px;
  z-index: 10; 
`;

// 오른쪽 말풍선 꼬리 (사용자 메시지)
export const UserTail = styled.div`
  border-top: 15px solid #f1f1f1;
  border-right: 15px solid transparent;
  content: "";
  position: absolute;
  top: 10px;
  right: -12px;  
  z-index: 10;  
`;

export const MessageInputContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  border-top: 1px solid #ddd;
  position: absolute;
  bottom: -73px;
  width: 100%; 
  z-index: 10; 
`;

export const MessageInput = styled.textarea`
  width: 80%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  resize: none;
  outline-color:  #495057;
`;

export const SendButton = styled.button`
  padding: 10px 15px;
  background-color: var(--green);
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
  
  &:hover {
    background-color: #44A253;
  }
`;

export const TopBar = styled.div`
  display: flex;
  align-items: center;
  padding: 10px;
  background: #f1f1f1;
  justify-content: space-between;
`;

// “나가기” 버튼 스타일 (원하는 스타일로 지정)
export const LeaveButton = styled.button`
  background-color: #ff5959;
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;

  &:hover {
    opacity: 0.85;
  }
`;
