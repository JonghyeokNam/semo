import styled from "styled-components";

export const applyWrapper = styled.div`
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

// applyComponent

export const Container = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 20px 0px;
    border-bottom: 1px solid #DEE2E6;
    cursor: pointer;
`;

export const Group = styled.div`
  display: flex;
  justify-content: flex-start;
  align-items: center;
  gap: 10px;
`;

export const Position = styled.div`
  font-size: 12px;
  background-color: var(--green);
  color: white;
  border-radius: 5px;
  padding: 5px 10px;
  font-weight: bold;
  width: 5rem;
  text-align: center;
`;

export const UserInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5px;
  width: 2.8rem;
`;

export const PostedTime = styled.div`
  font-size: 12px;
  color: #777;
`;

export const userName = styled.div`
  font-size: 14px;
  color: #333;
  font-weight: bold;
`;

export const AcceptButton = styled.button`
  font-size: 14px;
  color: #333333;
  background: none;
  border: none;
  cursor: pointer;

  &:hover {
    text-decoration: underline;
  }
`;

export const RejectButton = styled.button`
  font-size: 14px;
  color: #333333;
  background: none;
  border: none;
  cursor: pointer;

  &:hover {
    text-decoration: underline;
  }
`;

export const ResultText = styled.div`
  font-size: 16px;
  font-weight: bold;
  color: ${(props) => (props.isAccept ? "var(--green)" : "red")};
`;

export const CommentText = styled.div`
  font-size: 14px;
  color: #333;
  margin-bottom: 20px;
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
`;

export const VerticalLine = styled.div`
  width: 2px;
  height: 30px;
  background-color: #ccc;
`;

export const Content = styled.div`
  font-size: 14px;
  color: #717171;
`;