import styled from "styled-components";

// 알림 오버레이 스타일
export const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 999;
`

export const NotificationContent = styled.div`
  position: absolute;
  background-color: white;
  border-radius: 12px; /* 테두리 둥글게 처리 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2); /* 부드러운 그림자 */
  background-color: #fff; /* 박스 배경색 */
  top: 6.5rem;
  right: 8rem;
  width: 25%;
  height: 50%;
  z-index: 1000;
  display: flex;
  flex-direction: column;
  padding: 0 1.3rem;
`;

export const NotificationHead = styled.div`
  display: flex;
  width: 100%;
  height: 4rem;
  margin-bottom: 1rem;
`

export const Title = styled.h3`
    font-size: 1.3rem;
    font-weight: bold;
    text-align: center;
    margin-top: 1.3rem;
`;

export const Close = styled.button`
    position: absolute;
    top: 1rem;
    right: 0.8rem;
    font-size: 30px;
    background: none;
    border: none;
    cursor: pointer;
`;

export const NotificationBody = styled.div`
    height: 100%;
    overflow: auto;
      &::-webkit-scrollbar {
        width: 0.5rem;
      }
      &::-webkit-scrollbar-thumb {
        background: rgba(78, 185, 96); /* 스크롤바 색상 */
        border-radius: 10px; /* 스크롤바 둥근 테두리 */
      }
      &::-webkit-scrollbar-button:end:increment {
        /*  스크롤의 화살표가 포함된 영역   */
        display:block;
        height:8px;
        background-color: 0;
      }
`

export const Detail = styled.div`
    margin-bottom: 1rem;
    cursor: pointer;
`

export const NotificationBodyTop = styled.div`
    display: flex;
    justify-content: space-between;
    margin-bottom: 0.3rem;
`

export const NotificationBodyContent = styled.div`
    font-size: 14px;
    color: #495057;
    margin-bottom: 1.2rem;
    
`

export const BodyTitle = styled.h3`
  font-size: 0.9rem;
  font-weight: bold;
  color: #212529;
`

export const Date = styled.div`
  padding-right: 0.8rem;
  font-size: 0.8rem;
  color: #868E96;
  flex-wrap: wrap; /* 화면이 좁아지면 행을 여러 줄로 나누기 */
    
`




