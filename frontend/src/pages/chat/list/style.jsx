import styled from "styled-components";

// 이유진, 남종혁
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

// 오른쪽 영역(시간, 안 읽은 메시지 개수 등)을 묶을 컨테이너
export const RightSide = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end; /* 오른쪽 정렬 */
  gap: 5px;             /* 시간, 배지 간격 */
`;

// 안 읽은 메시지 수를 표시할 배지 스타일
export const Badge = styled.div`
  background-color: #FF5252;  /* 빨간색 계열 */
  color: #FFF;
  padding: 2px 6px;          /* 배지 내부 여백 */
  border-radius: 10px;       /* 둥근 테두리 */
  font-size: 11px;
  font-weight: bold;
  min-width: 18px;           /* 숫자가 한자리여도 최소 폭 */
  text-align: center;
`;