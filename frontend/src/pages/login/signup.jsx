import React from "react";
import * as S from "./style";
import { Dropdown } from "../../components/ui/dropdown";
import { useNavigate } from "react-router-dom"; // useNavigate 추가

const Signup = () => {
  const navigate = useNavigate(); // useNavigate 훅 사용
  const campusList = ["동대문캠퍼스", "성동캠퍼스", "동대문캠퍼스", "성동캠퍼스","동대문캠퍼스", "성동캠퍼스", "동대문캠퍼스", "성동캠퍼스","성동캠퍼스", "동대문캠퍼스", "성동캠퍼스","성동캠퍼스", "동대문캠퍼스", "성동캠퍼스","성동캠퍼스", "동대문캠퍼스", "성동캠퍼스"];
  const courseList = ["프론트엔드 과정", "백엔드 과정", "풀스택 과정"];
  const positionList = ["디자이너", "프론트엔드 개발자", "백엔드 개발자"];

  const handleComplete = () => {
    navigate("/"); // 버튼 클릭 시 '/'로 이동
  };

  return (
    <S.SignupWrapper>
      <S.SignupBox>
        <S.HeaderText>추가 정보</S.HeaderText>
        <S.ContentWrapper>
          <S.TextWrapper>
            <S.LabelText>소속된 캠퍼스를 선택해주세요.</S.LabelText>
            <Dropdown props={{ data: campusList }} />
          </S.TextWrapper>
          <S.TextWrapper>
            <S.LabelText>현재 진행 중인 과정명을 선택해주세요.</S.LabelText>
            <Dropdown props={{ data: courseList }} />
          </S.TextWrapper>
          <S.TextWrapper>
            <S.LabelText>희망하는 포지션을 선택해주세요.</S.LabelText>
            <Dropdown props={{ data: positionList }} />
          </S.TextWrapper>
          <S.TextWrapper>
            <S.LabelText>이메일을 입력해주세요.</S.LabelText>
            <S.InfoText>
              *캠퍼스 인증 결과는 입력하신 이메일로 전송됩니다.
            </S.InfoText>
            <S.Input placeholder="이메일을 입력해주세요." type="email" />
          </S.TextWrapper>
          <S.CompleteButtonWrapper>
            <S.CompleteButton onClick={handleComplete}>완료</S.CompleteButton>
          </S.CompleteButtonWrapper>
        </S.ContentWrapper>
      </S.SignupBox>
    </S.SignupWrapper>
  );
};

export default Signup;
