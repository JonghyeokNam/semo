import React from "react";
import * as S from "./style";
import { useNavigate } from "react-router-dom"; 
import SelectComponent from "../../components/ui/selectComponent";

const Signup = () => {
  const navigate = useNavigate(); // useNavigate 훅 사용
  const campusList = [
    { value: "campus1", label: "동대문캠퍼스" },
    { value: "campus2", label: "성동캠퍼스" },
  ];
  const courseList = [
    { value: "프론트엔드 과정", label: "프론트엔드 과정" },
    { value: "백엔드 과정", label: "백엔드 과정" },
    { value: "풀스택 과정", label: "풀스택 과정" },
  ];
  const positionList = [
    { value: "uiux", label: "UI/UX" },
    { value: "front", label: "프론트엔드 개발자" },
    { value: "back", label: "백엔드 개발자" },
    { value: "marketer", label: "마케터" },
  ];

  const handleComplete = () => {
    navigate("/"); // 버튼 클릭 시 '/'로 이동
  };

  return (
    <S.SignupWrapper>
      <S.SignupBox>
        <S.HeaderText>추가 정보</S.HeaderText>
        <S.ContentWrapper>
          <S.TextWrapper>
            
            <SelectComponent
              label="소속된 캠퍼스를 선택해주세요."
              options={campusList}
              placeholder="캠퍼스 선택"
            />
          </S.TextWrapper>
          <S.TextWrapper>
            
            <SelectComponent
              label="현재 진행 중인 과정명을 선택해주세요."
              options={courseList}
              placeholder="과정명 선택"
            />
          </S.TextWrapper>
          <S.TextWrapper>
           
            <SelectComponent
              label="희망하는 포지션을 선택해주세요."
              options={positionList}
              placeholder="포지션 선택"
            />
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
