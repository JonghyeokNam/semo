import React, { useState } from "react";
import * as S from "./style";
import formatRelativeTime from "../../../utils/formatTime";
import { useAuthStore } from "../../../store/useAuthStore";

export const CommentComponent = ({ key, comment, onUpdate, onDelete }) => {
  const { user } = useAuthStore();
  const loggedInUserId = user?.userId;
  const commentId = comment?.commentId;
  const commentAuthor = comment?.user?.username;
  const commentAuthorId = comment?.user?.userId;
  const commentContent = comment?.content;
  // const commentCreatedAt = comment?.createdAt; // 백엔드에서 댓글 생성시, create와 동시에 updatedAt도 올라가도록 해놔서 사용할 필요 없음.
  const commentUpdatedAt = comment?.updatedAt;

  const [isEditing, setIsEditing] = useState(false);
  const [editedContent, setEditedContent] = useState(commentContent);
  
  console.log(`loggedInUserId: ${loggedInUserId}`)
  console.log(`commentAuthorId: ${commentAuthorId}`)
  const handleEditClick = () => {
    setIsEditing(true); // 수정 상태로 전환
  };

  const handleSaveClick = () => {
    onUpdate(commentId, editedContent); // 부모 컴포넌트에 수정 요청 전달
    setIsEditing(false); // 수정 상태 종료
  };

  const handleCancelClick = () => {
    setIsEditing(false); // 수정 취소
    setEditedContent(comment.content); // 기존 내용으로 복구
  };

  const handleDeleteClick = () => onDelete(commentId);

  return (
    <S.CommentContainer>
      <S.TopBar>
      <S.CommentGroup>
        <S.ImgContainer>
            <S.ProfileImage src="/img/sesacHi.png" alt="프로필 이미지" />
        </S.ImgContainer>
        <S.CommentUserInfo>
          <S.userNameComment>{commentAuthor}</S.userNameComment>
          <S.PostedTime>{formatRelativeTime(commentUpdatedAt)}</S.PostedTime>
        </S.CommentUserInfo>
      </S.CommentGroup>

      {/* 수정 및 삭제 버튼 */}
      {loggedInUserId === commentAuthorId && (
        <S.CommentGroup>
          {!isEditing ? (
            <>
              <S.EditButton onClick={handleEditClick}>수정</S.EditButton>
              <S.DeleteButton onClick={handleDeleteClick}>삭제</S.DeleteButton>
            </>
          ) : (
            <>
              <S.SaveButton onClick={handleSaveClick}>저장</S.SaveButton>
              <S.CancelButton onClick={handleCancelClick}>취소</S.CancelButton>
            </>
          )}
        </S.CommentGroup>
      )}
      </S.TopBar>

      {/* 댓글 내용 */}
      {!isEditing ? (
        <S.CommentText>{commentContent}</S.CommentText>
      ) : (
        <S.CommentInput
          value={editedContent}
          onChange={(e) => setEditedContent(e.target.value)}
          placeholder="댓글을 수정하세요"
        />
      )}

      {/* 밑선 */}
      <S.Separator />
    </S.CommentContainer>
  );
};
