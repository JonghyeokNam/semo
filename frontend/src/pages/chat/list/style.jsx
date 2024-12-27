import styled from "styled-components";

export const ListContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Title = styled.div`
  font-size: 18px;
  font-weight: bold;
  color: #333;
`;

export const ChatItem = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  border-bottom: 1px solid #DEE2E6;
`;

export const Time = styled.div`
  font-size: 12px;
  color: #777;
`;

export const Group = styled.div`
  display: flex;
  justify-content: flex-start;
  align-items: center;
  gap: 10px;
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
  background-color: yellow;
`;

export const Content = styled.div`
  font-size: 12px;
  color: #777;
`;

export const UserName = styled.div`
  font-size: 14px;
  color: #333;
  font-weight: bold;
`;

export const UserInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5px;
`;