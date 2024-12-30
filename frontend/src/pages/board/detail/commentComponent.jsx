import React from "react";
import * as S from "./style";

export const CommentComponent = () => {
  return (
    <S.CommentContainer>
      <S.TopBar>
      <S.CommentGroup>
        <S.ImgContainer>
            <S.ProfileImage src="/img/sesacHi.png" alt="프로필 이미지" />
        </S.ImgContainer>
        <S.CommentUserInfo>
          <S.userNameComment>사용자 이름</S.userNameComment>
          <S.PostedTime>2024.09.30</S.PostedTime>
        </S.CommentUserInfo>
      </S.CommentGroup>

      {/* 수정 및 삭제 버튼 */}
      <S.CommentGroup>
        <S.EditButton>수정</S.EditButton>
        <S.DeleteButton>삭제</S.DeleteButton>
      </S.CommentGroup>
      </S.TopBar>

      {/* 댓글 내용 */}
      <S.CommentText>댓글 내용을 여기에 입력하세요. 이 부분은 사용자가 작성한 댓글이 표시됩니다.</S.CommentText>

      {/* 밑선 */}
      <S.Separator />
    </S.CommentContainer>
  );
};
