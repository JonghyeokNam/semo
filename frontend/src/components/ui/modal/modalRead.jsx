import React from "react";
import { IoClose } from "react-icons/io5"; 
import * as S from "./modalStyle"; 
import SelectComponent from "../selectComponent";

const ModalRead = ({ isOpen, onClose, formData }) => {
  // isOpen이 false일 경우 모달을 렌더링하지 않음
  if (!isOpen) return null;

  const positionList = [
    { value: "designer", label: "UI/UX" },
    { value: "frontend", label: "프론트엔드 개발자" },
    { value: "backend", label: "백엔드 개발자" },
    { value: "marketer", label: "마케터" },
  ];

  const handleClose = () => {
      onClose(); 
  };

  return (
    <S.ModalOverlay onClick={onClose}>
      <S.ModalContent onClick={(e) => e.stopPropagation()}>
        <S.CloseButton onClick={handleClose}>
          <IoClose />
        </S.CloseButton>
        <S.Column>
          <S.Title>지원 폼</S.Title>
          <SelectComponent
            label="희망하는 포지션"
            options={positionList}
            placeholder="포지션 선택"
            width="310px"
            isDisabled={true} // 수정 가능 여부에 따라 선택지를 비활성화
            value={formData.position}
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
            value={formData.aboutMe}
            readOnly={true} // 수정 가능 여부에 따라 읽기 전용 설정
            disabled={true} // 완전히 비활성화
          />
        </S.Column>
      </S.ModalContent>
    </S.ModalOverlay>
  );
};

export default ModalRead;
