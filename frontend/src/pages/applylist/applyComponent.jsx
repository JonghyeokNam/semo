import React, { useState } from "react";
import * as S from "./style";

const ApplyComponent = () => {
  const [action, setAction] = useState(""); // 상태 관리

  const handleAccept = () => {
    setAction("수락");
  };

  const handleReject = () => {
    setAction("거부");
  };

  return (
    <>
      <S.Container>
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
            안녕하세요! 저는 프론트엔드 개발을 공부 중인 새싹 과정 교육생입니다. 팀 협업 경험이 많아...
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
    </>
  );
};

export default ApplyComponent;