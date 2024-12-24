import React, { useState } from "react";
import * as S from "./style";
import { CommentComponent } from "./commentComponent";

// 댓글 UI
export const Comment = () => {
  const [comment, setComment] = useState("");

  const handleCommentChange = (e) => {
    setComment(e.target.value);
  };

  const handleCommentSubmit = () => {
    // 댓글 등록 로직 추가 (예: 서버로 보내기)
    console.log("댓글 등록:", comment);
    setComment(""); // 댓글 입력 칸 초기화
  };

  return (
    <S.CommentWrapper>
      <S.CommentGroup>
        <S.text>댓글</S.text>
        <S.count>2</S.count>
      </S.CommentGroup>

      {/* 댓글 입력 칸 */}
      <S.CommentInput
        value={comment}
        onChange={handleCommentChange}
        placeholder="댓글을 입력하세요"
      />

      {/* 댓글 등록 버튼 */}
      <S.CommentButton onClick={handleCommentSubmit}>댓글 등록</S.CommentButton>

      <CommentComponent/>
      <CommentComponent/>
    </S.CommentWrapper>
  );
};
