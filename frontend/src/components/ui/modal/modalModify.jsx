import React, { useState } from "react";
import { IoClose } from "react-icons/io5";
import * as S from "./modalStyle";
import SelectComponent from "../selectComponent";
import { useGetUserApplyFormsStore, useUpdateApplyFormStore } from "../../../store/useApplyStore";

const ModalModify = ({ isOpen, onClose, formData }) => {
  const { updateApplyForm } = useUpdateApplyFormStore();
  const { fetchUserApplyForms } = useGetUserApplyFormsStore();

  const [selectedPosition, setSelectedPosition] = useState(
    formData.position || ""
  );
  const [aboutMe, setAboutMe] = useState(formData.aboutMe.replace(/\\n/g, "\n") || "");

  // 모달이 열려있지 않으면 렌더링하지 않음
  if (!isOpen) return null;

  const positionList = [
    { value: "designer", label: "UI/UX" },
    { value: "frontend", label: "프론트엔드 개발자" },
    { value: "backend", label: "백엔드 개발자" },
    { value: "marketer", label: "마케터" },
  ];

  const handleComplete = async (e) => {
    e.stopPropagation();
      try {
        await updateApplyForm(formData.applyFormId, selectedPosition, aboutMe);
        fetchUserApplyForms();
        onClose();
      } catch (error) {
        console.log(error);
      }
  };

  const handlePositionChange = (selectedOption) => {
    setSelectedPosition(selectedOption?.value || "");
  };

  const handleAboutMeChange = (e) => {
    setAboutMe(e.target.value);
  };

  const handleClose = (e) => {
      setSelectedPosition(formData.position);
      setAboutMe(formData.aboutMe);
      onClose();
  };

  return (
    <S.ModalOverlay onClick={onClose}>
      <S.ModalContent onClick={(e) => e.stopPropagation()}>
        <S.CloseButton onClick={handleClose}>
          <IoClose />
        </S.CloseButton>
        <S.Column>
          <S.Title>지원 폼 수정</S.Title>
          <SelectComponent
            label="희망하는 포지션을 선택해 주세요."
            options={positionList}
            placeholder="포지션 선택"
            width="310px"
            value={selectedPosition}
            onChange={handlePositionChange}
          />
          <S.ContentTitle>어떤 팀원인가요? 자유롭게 소개해주세요.</S.ContentTitle>
          <S.ContentDescription>
            👉 간단한 자기소개와 사용 가능한 기술 스택 등을 작성해주세요. 노션이나
            깃허브 링크 등을 넣을 수 있습니다!
          </S.ContentDescription>
          <S.ContentInput
            placeholder="내용을 입력하세요"
            value={aboutMe}
            onChange={handleAboutMeChange}
          />
          <S.CompleteButtonWrapper>
            <S.CompleteButton onClick={handleComplete}>
              완료
            </S.CompleteButton>
          </S.CompleteButtonWrapper>
        </S.Column>
      </S.ModalContent>
    </S.ModalOverlay>
  );
};

export default ModalModify;
