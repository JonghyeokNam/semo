import styled from "styled-components";
import { FaEye, FaRegComment } from "react-icons/fa";
import { Link } from "react-router-dom";
import { FaRegBookmark } from "react-icons/fa";

// 이유진
export const BoardListContainer = styled.div`
  padding: 20px 30px;
  border-bottom: 1px solid #DEE2E6;
  background-color: #fff;
  position: relative; 
`;

export const LinkContainer = styled(Link)`
  display: block;
  width: 100%;
`;

export const LinkContainer100 = styled(Link)`
  display: block;
  width: 100%;
`;


export const Row = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
`;

export const TitleContainer = styled.div`
  display: flex;
  align-items: center;

`;

export const Title = styled.div`
  font-size: 20px;
  font-weight: bold;
  color: #212529;
`;

export const Badge = styled.div`
  background-color: ${(props) => (props.$isClosed ? "#BDBDBD" : "var(--green)")};
  color: #fff;
  font-size: 10px;
  font-weight: bold;
  padding: 6px 10px;
  margin-right: 10px;
  border-radius: 800px;
`;

export const FinishBadge = styled.div`
  background-color: #BDBDBD;
  color: #fff;
  font-size: 10px;
  font-weight: bold;
  padding: 6px 10px;
  margin-right: 10px;
  border-radius: 800px;
`;

export const RightTop = styled.div`
  position: absolute;
  top: 30px;
  right: 30px;
  font-size: 35px;
  color:${(props) => (props.$isParticipated ? "var(--green)" : "#343330")};
`;

export const ApplyButton = styled.div`
  font-size: 16px;
  background-color: var(--green);
  color: white;
  border-radius: 10px;
  padding: 8px 16px;
  font-weight: bold;

  &:hover {
    background-color: #2d8f44;
  }
`;

export const Content = styled.div`
  font-size: 14px;
  color: #495057;
  margin-bottom: 15px;
`;

export const InfoContainer = styled.div`
  display: flex;
  align-items: center;
  font-size: 12px;
  color: #868E96;
  flex-wrap: wrap; /* 화면이 좁아지면 행을 여러 줄로 나누기 */

`;

export const InfoItem = styled.div`
  display: flex;
  align-items: center;
  margin-right: 15px;  /* 각 항목 사이에 간격 추가 */
`;

export const Icon = styled.div`
  margin-right: 5px;
  font-size: 12px;
  color: #868E96;
`;

export const FinishApplicantInfo = styled.div`
  display: flex;
  gap: 8px;
  font-size: 12px;
  color: #495057;
`;


export const ApplicantInfo = styled.div`
  display: flex;
  gap: 8px;
  font-size: 12px;
  color: ${(props) => (props.$isClosed ? "#495057" : "var(--green)")};
`;

export const EyeIcon = styled(FaEye)`
  margin-right: 5px;
  font-size: 12px;
  color: #868E96;
`;

export const CommentIcon = styled(FaRegComment)`
  margin-right: 5px;
  font-size: 12px;
  color: #868E96;
`;

export const IconWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const StyledBookmarkIcon = styled(FaRegBookmark)`
  font-size: 25px;
  color: ${({ $isActive }) => ($isActive ? "var(--green)" : "#333")};
`;
