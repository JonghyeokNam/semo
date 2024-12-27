import React, { useState } from "react";
import * as S from "./style";
import ModalRead from "../../components/ui/modal/modalRead";

const ApplyComponent = () => {
  const [action, setAction] = useState(""); // 수락/거부 상태 관리

  const handleAccept = (e) => {
    e.stopPropagation(); // 이벤트 전파 중단
    e.preventDefault(); // 기본 동작 방지
    const isConfirmed = window.confirm("수락 하시겠습니까?");
    if (isConfirmed) {
      setAction("수락");
    }
  };

  const handleReject = (e) => {
    e.stopPropagation(); // 이벤트 전파 중단
    e.preventDefault(); // 기본 동작 방지
    const isConfirmed = window.confirm("거부 하시겠습니까?");
    if (isConfirmed) {
      setAction("거부");
    }
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

  const content = '안녕하세요! 저는 프론트엔드 개발을 공부 중인 새싹 과정 교육생입니다. 팀 협업 경험이 많아...'

  // 글자수 제한 함수: 25글자 이상이면 "..." 추가
  const truncate = (str, n) => {
    return str?.length > n ? str.substr(0, n - 1) + "..." : str;
  };

  return (
    <>
      <S.Container onClick={modalOpen}>
        <S.Group>
          <S.Position>프론트엔드</S.Position>
          <S.Group>
            <S.ImgContainer>
              <S.ProfileImage src="/img/sesacHi.png" alt="프로필 이미지" />
            </S.ImgContainer>
            <S.UserInfo>
              <S.userName>이유진</S.userName>
              <S.PostedTime>5시간 전</S.PostedTime>
            </S.UserInfo>
          </S.Group>
          <S.VerticalLine />
          <S.Content>
            {truncate(content, 25)}
          </S.Content>
        </S.Group>
        <S.Group>
          {!action && ( // action이 없을 때만 버튼 렌더링
            <>
              <S.AcceptButton onClick={handleAccept}>수락</S.AcceptButton>
              <S.RejectButton onClick={handleReject}>거부</S.RejectButton>
            </>
          )}
        </S.Group>
        {action && (
          <S.ResultText isAccept={action === "수락"}>{action}</S.ResultText>
        )}
      </S.Container>
      <ModalRead isOpen={open} onClose={closeModal} />
    </>
  );
};

export default ApplyComponent;
