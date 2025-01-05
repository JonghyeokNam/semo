import React, { useEffect, useState } from "react";
import * as S from "./style";
import { useNavigate } from "react-router-dom";
import SelectComponent from "../../components/ui/selectComponent";
import useSignupStore from "../../store/useSignupStore";
import { useUpdateUserStore } from "../../store/useAuthStore"; // useUpdateUserStore 추가

const Signup = () => {
  const navigate = useNavigate();
  const { campuses, courses, fetchCampuses, fetchCourses, loading } = useSignupStore();
  const { updateUserInfo, isUpdating } = useUpdateUserStore(); // 사용자 정보 업데이트
  const [selectedCampus, setSelectedCampus] = useState(null); // 선택된 캠퍼스 상태
  const [selectedCourse, setSelectedCourse] = useState(null); // 선택된 과정 상태
  const [selectedPosition, setSelectedPosition] = useState(null); // 선택된 포지션 상태
  const [userEmail, setUserEmail] = useState(""); // 이메일 상태

  useEffect(() => {
    fetchCampuses(); // 캠퍼스 목록을 가져옵니다.
  }, [fetchCampuses]);

  const handleCampusChange = (selectedOption) => {
    setSelectedCampus(selectedOption);
    if (selectedOption) {
      fetchCourses(selectedOption.value); // 캠퍼스가 선택되면 해당 캠퍼스의 과정을 가져옵니다.
    }
  };

  const handleCourseChange = (selectedOption) => {
    setSelectedCourse(selectedOption);
  };

  const handlePositionChange = (selectedOption) => {
    setSelectedPosition(selectedOption);
  };

  const handleComplete = async () => {
    // 업데이트할 사용자 정보 객체 생성
    const userInfo = {
      userEmail,
      position: selectedPosition?.value,
      campusName: selectedCampus?.label,
      courseName: selectedCourse?.label,
    };

    console.log(userInfo);

    try {
      await updateUserInfo(userInfo); // 사용자 정보 업데이트 요청
    } catch (error) {
      console.error("프로필 저장 중 오류 발생:", error);
    }
    navigate("/home"); // 완료 후 홈으로 이동
  };


  // 캠퍼스 목록을 SelectComponent에서 사용할 형식으로 변환
  const campusList = campuses && campuses.length > 0
    ? campuses.map(campus => ({
        value: campus.campusId,
        label: campus.name
      }))
    : [];

  // 선택된 캠퍼스에 따른 과정 목록을 SelectComponent에서 사용할 형식으로 변환
  const courseList = courses && courses.length > 0
    ? courses.filter(course => course.campusId === selectedCampus?.value) // 선택된 캠퍼스에 맞는 과정만 필터링
      .map(course => ({
        value: course.courseId,
        label: course.name
      }))
    : [];

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
              onChange={handleCampusChange}
              isLoading={loading && !campuses.length}
            />
          </S.TextWrapper>
          <S.TextWrapper>
            <SelectComponent
              label="현재 진행 중인 과정명을 선택해주세요."
              options={courseList}
              placeholder="과정명 선택"
              onChange={handleCourseChange}
              disabled={loading || !selectedCampus} // 캠퍼스 선택 후 로딩 완료되어야 활성화
              isLoading={loading && !courses.length}
            />
          </S.TextWrapper>
          <S.TextWrapper>
            <SelectComponent
              label="희망하는 포지션을 선택해주세요."
              options={[
                { value: "designer", label: "UI/UX" },
                { value: "frontend", label: "프론트엔드 개발자" },
                { value: "backend", label: "백엔드 개발자" },
                { value: "marketer", label: "마케터" },
              ]}
              placeholder="포지션 선택"
              onChange={handlePositionChange}
            />
          </S.TextWrapper>
          <S.TextWrapper>
            <S.LabelText>이메일을 입력해주세요.</S.LabelText>
            <S.InfoText>
              *캠퍼스 인증 결과는 입력하신 이메일로 전송됩니다.
            </S.InfoText>
            <S.Input
              placeholder="이메일을 입력해주세요."
              type="email"
              value={userEmail}
              onChange={(e) => setUserEmail(e.target.value)} // 이메일 입력 값 변경 처리
            />
          </S.TextWrapper>
          <S.CompleteButtonWrapper>
            <S.CompleteButton onClick={handleComplete} disabled={isUpdating}>완료</S.CompleteButton> {/* 업데이트 중에는 버튼 비활성화 */}
          </S.CompleteButtonWrapper>
        </S.ContentWrapper>
      </S.SignupBox>
    </S.SignupWrapper>
  );
};

export default Signup;
