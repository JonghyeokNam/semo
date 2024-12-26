import React from "react";
import { IoClose } from "react-icons/io5"; 
import * as S from "./modalStyle"; 
import SelectComponent from "../selectComponent";

const ModalModify = ({ isOpen, onClose }) => {
  // isOpen이 false일 경우 모달을 렌더링하지 않음
  if (!isOpen) return null;

  const positionList = [
    { value: "uiux", label: "UI/UX" },
    { value: "front", label: "프론트엔드 개발자" },
    { value: "back", label: "백엔드 개발자" },
    { value: "marketer", label: "마케터" },
  ];

  const handleComplete = (e) => {
    const userConfirmed = window.confirm("수정하시겠습니까?");
    if (userConfirmed) {
      onClose();
    }
    e.stopPropagation();
  };

  const handleClose = (e) => {
    // X 아이콘 클릭 시 확인 알림 창
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
          <S.Title>지원 폼 수정</S.Title>
          <SelectComponent
            label="희망하는 포지션을 선택해 주세요."
            options={positionList}
            placeholder="포지션 선택"
            width="310px"
          />
          {/* 댓글 입력 칸 */}
          <S.ContentTitle>
            어떤 팀원인가요? 자유롭게 소개해주세요.
          </S.ContentTitle>
          <S.ContentDescription>
            👉 간단한 자기소개와 사용 가능한 기술 스택 등을 작성해주세요. 노션이나 깃허브 링크 등을 넣을 수 있습니다!
          </S.ContentDescription>
          <S.ContentInput
            placeholder="내용을 입력하세요"
          />
          <S.CompleteButtonWrapper>
            <S.CompleteButton onClick={handleComplete}>완료</S.CompleteButton>
          </S.CompleteButtonWrapper>
        </S.Column>
      </S.ModalContent>
    </S.ModalOverlay>
  );
};

export default ModalModify;
