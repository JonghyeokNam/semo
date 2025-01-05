import React, { useState, useRef } from "react";
import * as S from "./style";
import Editor from "./quill";
import Quill from "quill";
import SelectComponent from "../../../components/ui/selectComponent";
import CustomCalendar from "../../../components/ui/calendar";
import useMediaQueries from "../../../hooks/useMediaQueries";
import { useUpdateBoardStore } from "../../../store/useBoardStore";
import { Link, useLocation } from "react-router-dom";
import moment from "moment";

const Delta = Quill.import("delta");

const Index = () => {
  const location = useLocation();
  const { boardData } = location.state || {};

  const { isDesktop, isTablet } = useMediaQueries();

  // Zustand Store 상태 및 함수
  const { updateBoard, loading, error } = useUpdateBoardStore();

  // 입력값 상태
  const [formData, setFormData] = useState({
    title: boardData?.title || "",
    content: boardData?.content || "",
    recruitmentTypes: boardData?.recruitmentTypes || [],
    recruitmentCount: boardData?.recruitmentCount || 0,
    recruitmentField: boardData?.recruitmentField || "",
    recruitmentMethod: boardData?.recruitmentMethod || "",
    recruitmentDeadline: boardData?.recruitmentDeadline || null,
    progressPeriod: boardData?.progressPeriod || "",
  });

  console.log(formData);
  console.log("deadline:", formData.recruitmentDeadline);
  console.log("moment(deadline):", moment(formData.recruitmentDeadline));
  console.log("moment(deadline).toDate():", moment(formData.recruitmentDeadline).toDate());

  const quillRef = useRef(null);

  const handleQuillContentChange = (delta, oldDelta, source, quill) => {
    if (source === "user") {
      if (quill) {
        const entireText = quill.getText();
        setFormData((prev) => ({ ...prev, content: entireText }));
      }
    }
  };

  const handleRecruitmentCountChange = (value) => {
    setFormData((prev) => ({
      ...prev,
      recruitmentCount: isNaN(value) ? 0 : value,
    }));
  };

  const recruitmentFieldData = [
    { value: "프로젝트", label: "프로젝트" },
    { value: "스터디", label: "스터디" },
  ];

  const recruitmentCountData = [
    { value: 0, label: "인원 미정" },
    { value: 1, label: "1명" },
    { value: 2, label: "2명" },
    { value: 3, label: "3명" },
    { value: 4, label: "4명" },
    { value: 5, label: "5명" },
    { value: 6, label: "6명" },
    { value: 7, label: "7명" },
    { value: 8, label: "8명" },
    { value: 9, label: "9명" },
    { value: 10, label: "10명" },
  ];

  const processMethodData = [
    { value: "online", label: "온라인" },
    { value: "offline", label: "오프라인" },
    { value: "both", label: "온/오프라인" },
  ];

  const progressPeriodData = [
    { value: "기간 미정", label: "기간 미정" },
    { value: "1개월", label: "1개월" },
    { value: "2개월", label: "2개월" },
    { value: "3개월", label: "3개월" },
    { value: "4개월", label: "4개월" },
    { value: "5개월", label: "5개월" },
    { value: "6개월 이상", label: "6개월 이상" },
  ];

  const recruitmentTypeData = [
    { value: "designer", label: "UI/UX" },
    { value: "frontend", label: "프론트엔드" },
    { value: "backend", label: "백엔드" },
    { value: "marketer", label: "마케터" },
  ];

  // 입력값 변경 핸들러
  const handleInputChange = (field, value) => {
    setFormData((prev) => ({ ...prev, [field]: value }));
  };

  const handleRecruitmentTypeChange = (selectedOptions) => {
    const types = selectedOptions.map((option) => option.value);
    setFormData((prev) => ({ ...prev, recruitmentTypes: types }));
  };

  const handleCalendarChange = (selectedDate) => {
    // 선택된 날짜를 서버 요구 형식으로 변환
    const offset = new Date().getTimezoneOffset() * -1; // 시간대 보정
    const timezoneOffset = `${offset >= 0 ? "+" : "-"}${String(
      Math.abs(offset / 60)
    ).padStart(2, "0")}:00`;

    const formattedDeadline = `${selectedDate.toISOString().split("T")[0]}T${
      selectedDate.toTimeString().split(" ")[0]
    }${timezoneOffset}`;

    setFormData((prev) => ({
      ...prev,
      recruitmentDeadline: formattedDeadline, // 서버 요구 형식으로 설정
    }));
  };

  const getStringValue = (item) => {
    // 예) {value: "프로젝트", label: "프로젝트"} → "프로젝트"
    if (item && typeof item === "object" && item.value) {
      return item.value;
    }
    // 그 외에는 문자열 그대로
    return item;
  };

  // 수정하기 버튼 클릭 핸들러
  const handleSubmit = async () => {
    // 필수 항목 검증
    if (
      !formData.title ||
      !formData.content ||
      formData.recruitmentTypes.length === 0
    ) {
      alert("필수 항목을 모두 입력해주세요.");
      return;
    }

    const requestData = {
      title: formData.title,
      content: formData.content,
      recruitmentTypes: formData.recruitmentTypes,
      recruitmentCount: formData.recruitmentCount,
      recruitmentField: getStringValue(formData.recruitmentField),
      recruitmentMethod: getStringValue(formData.recruitmentMethod),
      recruitmentDeadline: formData.recruitmentDeadline, // 그대로 저장됨
      progressPeriod: getStringValue(formData.progressPeriod),
    };

    console.log("최종 전송 데이터:", JSON.stringify(requestData, null, 2));

    try {
      await updateBoard(boardData.boardId, requestData);
      alert("게시글이 성공적으로 수정되었습니다!");
    } catch (err) {
      console.error("에러 발생:", err.message);
      alert(`게시글 수정 실패: ${err.message}`);
    }
  };

  const handleCancel = (e) => {
    const confirmCancel = window.confirm(
      "정말 취소하시겠습니까? 작성 내용이 모두 삭제됩니다."
    );
    if (!confirmCancel) {
      e.preventDefault(); // 이동을 막음
    }
  };

  return (
    <S.Container>
      <S.FormSection>
        <S.title>1. 프로젝트 기본 정보를 입력해주세요.</S.title>
        <S.Separator />
        <S.Grid $isDesktop={isDesktop} $isTablet={isTablet}>
          <S.Column>
            <SelectComponent
              label="모집 구분"
              options={recruitmentFieldData}
              placeholder="스터디 / 프로젝트"
              width="270px"
              value={formData.recruitmentField}
              onChange={(option) =>
                handleInputChange("recruitmentField", option.value)
              } // 이거 필요하면 바꿔야됨, 아니면 create를 수정하거나
            />
          </S.Column>
          <S.Column>
            <SelectComponent
              label="모집 인원"
              options={recruitmentCountData}
              placeholder="인원 미정 ~ 10명 이상"
              width="270px"
              value={formData.recruitmentCount}
              onChange={(option) => {
                console.log("Select changed:", option.value);
                handleRecruitmentCountChange(option.value);
              }}
            />
          </S.Column>
          <S.Column>
            <SelectComponent
              label="진행 방식"
              options={processMethodData}
              placeholder="온라인 / 오프라인"
              width="270px"
              value={formData.recruitmentMethod}
              onChange={(option) =>
                handleInputChange("recruitmentMethod", option.value)
              }
            />
          </S.Column>

          <S.Column>
            <SelectComponent
              label="진행 기간" 
              options={progressPeriodData}
              placeholder="기간 미정 ~ 6개월 이상"
              width="270px"
              value={formData.progressPeriod}
              onChange={(option) => handleInputChange("progressPeriod", option.value)}
            />
          </S.Column>
          <S.Column>
            <S.Label>모집 마감일</S.Label>
            <CustomCalendar
              value={
                formData.recruitmentDeadline
                  ? moment(formData.recruitmentDeadline).toDate()
                  : null
                }
              onChange={handleCalendarChange}
            />
          </S.Column>
          <S.Column>
            <SelectComponent
              label="모집 포지션"
              options={recruitmentTypeData}
              isMulti={true}
              placeholder="프론트엔드, 백엔드..."
              width="270px"
              value={formData.recruitmentTypes}
              onChange={(selectedOptions) => {
                const types = selectedOptions.map((opt) => opt.value);
                handleInputChange("recruitmentTypes", types);
              }}
            />
          </S.Column>
        </S.Grid>
      </S.FormSection>
      <S.FormSection>
        <S.title>2. 프로젝트에 대해 소개해주세요.</S.title>
        <S.Separator />
        <S.Column style={{ width: "100%" }}>
          <S.Label>제목</S.Label>
          <S.TextInput
            placeholder="글 제목을 입력해주세요."
            value={formData.title}
            onChange={(e) => handleInputChange("title", e.target.value)}
          />
          <S.EditorContainer>
            <Editor
              ref={quillRef}
              readOnly={false}
              placeholder="내용을 작성해주세요."
              defaultValue={new Delta().insert(formData.content.replace(/\\n/g, "\n") || "")}
              onTextChange={handleQuillContentChange}
              style={{ minHeight: "200px" }}
            />
          </S.EditorContainer>
          <S.ButtonContainer>
            <Link to="/" onClick={handleCancel}>
              <S.CancelButton>취소</S.CancelButton>
            </Link>
            <Link to="/">
              <S.SubmitButton onClick={handleSubmit} disabled={loading}>
                {loading ? "수정 중..." : "수정하기"}
              </S.SubmitButton>
            </Link>
          </S.ButtonContainer>
        </S.Column>
      </S.FormSection>
    </S.Container>
  );
};

export default Index;
