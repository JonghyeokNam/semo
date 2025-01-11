import styled from "styled-components";

// 이유진
// 전체 화면 Wrapper
export const LoginWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
`;

// 박스 스타일
export const LoginBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* 왼쪽 정렬 */
  justify-content: center;
  box-shadow: 0px 4px 48px rgba(0, 0, 0, 0.1); /* 그림자 설정 */
  border-radius: 10px; /* 박스 모서리 둥글게 */
  background-color: #ffffff;
  padding: 90px;
`;

// 로고 이미지 스타일
export const Logo = styled.img`
  width: 60px;
  margin-bottom: 20px;
`;

// 텍스트 1 (큰 제목)
export const Text1 = styled.h1`
  font-size: 40px;
  color: #343a40;
  margin-bottom: 10px;
  font-weight:700;
`;

// 텍스트 2 (설명 텍스트)
export const Text2 = styled.p`
  font-size: 18px;
  color: #343a40;
  margin-bottom: 40px;
  line-height: 1.3;
`;

// 카카오 버튼 Wrapper
export const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  margin: 20px 0;
`;

// 카카오 버튼
export const KakaoButton = styled.button`
  background-color: #fee500; /* 카카오 노란색 */
  border: none;
  border-radius: 10px;
  color: #000000;
  font-size: 18px;
  font-weight:600;
  padding: 15px 20px;
  cursor: pointer;
  width: 100%;
  text-align: center;

  &:hover {
    background-color: #fdd835;
  }
`;

// 약관 텍스트
export const TermsText = styled.p`
  font-size: 15px;
  color: #999999;
  text-align: center; /* 중앙 정렬 */
  width: 100%;
  margin-top: 10px;
  line-height: 1.3;
`;


//signup
// 전체 Wrapper
export const SignupWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
`;

// 박스 스타일
export const SignupBox = styled.div`
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  justify-content: flex-start;
  box-shadow: 0px 4px 48px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  background-color: #ffffff;
  padding: 80px 200px;
  gap: 126px;
`;

// 헤더 텍스트 (왼쪽 상단 배치)
export const HeaderText = styled.h1`
  font-size: 30px;
  font-weight: 700;
  color: #343A40;
`;

// 컨텐츠 Wrapper
export const ContentWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 30px; 
`;

// 텍스트 Wrapper
export const TextWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: flex-start;
`;

// 레이블 텍스트
export const LabelText = styled.label`
  font-size: 16px;
  font-weight: 600;
  color: #333333;
`;

// 안내 문구 스타일
export const InfoText = styled.p`
  font-size: 14px;
  color: #646464;
  margin-top: -5px;
`;

// 이메일 입력칸
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
// 완료 버튼 Wrapper
export const CompleteButtonWrapper = styled.div`
  width: 100%;
  margin-top: 20px;
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