import styled from "styled-components";

export const CalendarContainer = styled.div`
  position: relative;
  display: inline-block;
  margin-bottom: 20px;
`;

export const DropdownButton = styled.button`
  background-color: #fff;
  border: 1px solid #cccccc;
  padding: 10px;
  display: flex;
  align-items: center;
  cursor: pointer;
  font-size: 16px;
  color: ${(props) => (props.selectedDate ? "#333333" : "#cccccc")}; 
  border-radius: 4px;
  width: 100%;
  min-width: 270px;
  max-width: 270px;
  height: 38px;
  justify-content: space-between;

  &:focus {
    outline: none;
    border-color: #007bff;
  }
`;

export const CalendarWrapper = styled.div`
  position: absolute;
  top: 40px;
  left: 0;
  z-index: 10;
  background-color: white;
  border: 1px solid #cccccc;
  border-radius: 4px;

  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  display: ${(props) => (props.isOpen ? "block" : "none")};
  padding: 10px;

  .react-calendar {
    border-radius: 10px;
    border: 1px solid var(--festie-gray-400, #c8c8c8); 
  }

  .react-calendar__navigation__label > span {
    /* 달력 상단 년/월 글씨 커스텀 */
    color: var(--festie-gray-800, #3a3a3a);
    font-family: SUIT Variable;
    font-size: 13px;
    font-weight: 500;
    line-height: 140%;
  }

  .react-calendar__month-view__days__day--weekend {
    /* 주말 글씨 빨간색 없애기 */
    color: var(--festie-gray-800, #3a3a3a);
  }

  .react-calendar__tile:enabled:hover,
  .react-calendar__tile:enabled:focus {
    /* hover 했을 때 색상 변경 */
    background: var(--green);
  }

  .react-calendar__tile--now {
    /* 오늘 날짜 하이라이트 커스텀 */
    background: white;
    color: var(--green);
  }

  .react-calendar__tile--active {
    background: var(--green);
    color: white;
  }
`;

export const ArrowIcon = styled.img`
  width: 10px;
  height: 10px;
  transform: ${(props) => (props.isOpen ? "rotate(180deg)" : "rotate(0deg)")};
  transition: transform 0.3s ease;
`;

export const CalendarIcon = styled.div`
  margin-left: 8px; 
  font-size: 16px;  
  color: #cccccc;  
  display: flex;
  align-items: center; 
`;