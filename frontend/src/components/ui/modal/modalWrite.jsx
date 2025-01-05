import React, { useState } from "react";
import { IoClose } from "react-icons/io5"; 
import * as S from "./modalStyle"; 
import SelectComponent from "../selectComponent";
import useApplyStore from "../../../store/useApplyStore";
import useBoardListStore from "../../../store/useBoardListStore";

const Modal = ({ isOpen, onClose, boardId, setIsParticipated }) => {
  const { submitApplication, isLoading, isError, error } = useApplyStore();
  const { boards } = useBoardListStore();
  const [selectedPosition, setSelectedPosition] = useState("");
  const [aboutMe, setAboutMe] = useState("");
  

  if (!isOpen) return null;

  const positionList = [
    { value: "frontend", label: "프론트엔드 개발자" },
    { value: "backend", label: "백엔드 개발자" },
    { value: "designer", label: "UI/UX" },
    { value: "marketer", label: "마케터" },
  ];

  const handleComplete = () => {
    const positionName = selectedPosition;
    
    submitApplication(boardId, positionName, aboutMe); // 지원 폼 제출
    console.log("positionName", positionName);
    console.log("aboutMe", aboutMe);

    // 서버에 데이터 제출 후 모달 닫기
    setIsParticipated(true); // 지원 상태 업데이트
    onClose();
  };

  const handleClose = () => {
    const confirmClose = window.confirm(
      "작성 중인 내용은 저장되지 않습니다. 정말 닫으시겠습니까?"
    );
    if (confirmClose) {
      onClose();
    }
  };

  return (
    <S.ModalOverlay onClick={onClose}>
      <S.ModalContent onClick={(e) => e.stopPropagation()}>
        <S.CloseButton onClick={handleClose}>
          <IoClose />
        </S.CloseButton>
        <S.Column>
          <S.Title>지원 폼 작성</S.Title>
          <SelectComponent
            label="희망하는 포지션을 선택해 주세요."
            options={positionList}
            placeholder="포지션 선택"
            value={selectedPosition}
            onChange={(selectedOption) => setSelectedPosition(selectedOption?.value || "")} 
            width="310px"
          />
          <S.ContentTitle>
            어떤 팀원인가요? 자유롭게 소개해주세요.
          </S.ContentTitle>
          <S.ContentDescription>
            👉 간단한 자기소개와 사용 가능한 기술 스택 등을 작성해주세요. 노션이나 깃허브 링크 등을 넣을 수 있습니다!
          </S.ContentDescription>
          <S.ContentInput
            placeholder="내용을 입력하세요"
            value={aboutMe}
            onChange={(e) => setAboutMe(e.target.value)}
          />
          {isError && <div>지원 폼 제출에 실패했습니다: {error}</div>}
          <S.CompleteButtonWrapper>
            <S.CompleteButton onClick={handleComplete} disabled={isLoading}>
              {isLoading ? "제출 중..." : "완료"}
            </S.CompleteButton>
          </S.CompleteButtonWrapper>
        </S.Column>
      </S.ModalContent>
    </S.ModalOverlay>
  );
};

export default Modal;
