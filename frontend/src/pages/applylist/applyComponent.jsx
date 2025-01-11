import React, { useState } from "react";
import * as S from "./style";
import ModalRead from "../../components/ui/modal/modalRead";
import { truncate } from "../../utils/truncateText";
import formatRelativeTime from "../../utils/formatTime";
import { useGetBoardApplyFormStore, useSetApplyFormStatusStore } from "../../store/useApplyStore";
import { replaceNewlinesWithSpace } from "../../utils/replaceUtil";

// 이유진
const ApplyComponent = ({ formData }) => {
  const [action, setAction] = useState(formData.status); // 수락/거부 상태 관리
  const { fetchApplyFormStatus } = useSetApplyFormStatusStore();
  const { fetchBoardApplyForms } = useGetBoardApplyFormStore();

  const handleAccept = (e) => {
    e.stopPropagation(); // 이벤트 전파 중단
    e.preventDefault(); // 기본 동작 방지
    setAction("수락");
    fetchApplyFormStatus(formData.applyFormId, "수락");
    fetchBoardApplyForms(formData.boardId);
  };

  const handleReject = (e) => {
    e.stopPropagation(); // 이벤트 전파 중단
    e.preventDefault(); // 기본 동작 방지
    setAction("거부");
    fetchApplyFormStatus(formData.applyFormId, "거부");
    fetchBoardApplyForms(formData.boardId);
  };

  const [open, setOpen] = useState(false); //모달창 상태관리

  const modalOpen = (e) => {
    console.log("모달 오픈 클릭!");
    e.stopPropagation(); // 이벤트 전파 중단
    e.preventDefault(); // 기본 동작 방지
    setOpen(true);
  };

  const closeModal = () => {
    setOpen(false); 
  };

  const positionList = [
    { value: "designer", label: "UI/UX" },
    { value: "frontend", label: "프론트엔드" },
    { value: "backend", label: "백엔드" },
    { value: "marketer", label: "마케터" },
  ];

    // formData.position에 해당하는 라벨 찾기
    const positionLabel = positionList.find(
      (position) => position.value === formData.position
    )?.label || "알 수 없는 포지션";

  return (
    <>
      <S.Container onClick={modalOpen}>
        <S.Group>
          <S.Position>{positionLabel}</S.Position>
          <S.Group>
            <S.ImgContainer>
              <S.ProfileImage src="/img/sesacHi.png" alt="프로필 이미지" />
            </S.ImgContainer>
            <S.UserInfo>
              <S.userName>{formData.username}</S.userName>
              <S.PostedTime>{formatRelativeTime(formData.createdAt)}</S.PostedTime>
            </S.UserInfo>
          </S.Group>
          <S.VerticalLine />
          <S.Content>
            {replaceNewlinesWithSpace(truncate(formData.aboutMe, 40))}
          </S.Content>
        </S.Group>
        <S.Group>
          {action === "대기" && ( // action이 없을 때만 버튼 렌더링
            <>
              <S.AcceptButton onClick={handleAccept}>수락</S.AcceptButton>
              <S.RejectButton onClick={handleReject}>거부</S.RejectButton>
            </>
          )}
        </S.Group>
        {action !== "대기" && (
          <S.ResultText isAccept={action === "수락"}>{action}</S.ResultText>
        )}
      </S.Container>
      <ModalRead isOpen={open} onClose={closeModal} formData={formData} />
    </>
  );
};

export default ApplyComponent;
