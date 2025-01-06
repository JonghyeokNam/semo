import React, { useState, useEffect } from "react";
import * as S from "./style";
import { CommentComponent } from "./commentComponent";
import { useCommentStore } from "../../../store/useCommentStore";

// 이유진, 주현우
export const Comment = ({ boardId }) => {

  const [comment, setComment] = useState("");
  const { comments, fetchComments, addComment, updateComment, deleteComment, loading, error } = useCommentStore(); 

  // 댓글 목록 Fetch
  useEffect(() => {
    if (boardId) {
      fetchComments(boardId);
    }
  }, [boardId, fetchComments]);

  const handleCommentChange = (e) => {
    setComment(e.target.value);
  };

  const handleCommentSubmit = async () => {
    if (!comment.trim()) {
      alert("댓글을 입력하세요.");
      return;
    }

    try {
      await addComment(boardId, comment); // 댓글 추가 함수 호출
      setComment(""); // 입력 필드 초기화
    } catch (error) {
      console.error("댓글 등록 오류:", error);
      alert("댓글 등록 중 오류가 발생했습니다.");
    }
  };

  const handleDelete = async (commentId) => {
    try {
      await deleteComment(boardId, commentId); // 댓글 삭제
      console.log("댓글이 삭제되었습니다.");
    } catch (error) {
      console.error("댓글 삭제 실패:", error);
    }
  };


  return (
    <S.CommentWrapper>
      <S.CommentGroup>
        <S.text>댓글</S.text>
        <S.count>{comments.length}</S.count>
      </S.CommentGroup>

      {/* 댓글 입력 칸 */}
      <S.CommentInput
        value={comment}
        onChange={handleCommentChange}
        placeholder="댓글을 입력하세요"
      />

      {/* 댓글 등록 버튼 */}
      <S.CommentButton onClick={handleCommentSubmit}>댓글 등록</S.CommentButton>
      
      {/* 댓글 목록 */}
      {
        comments.map((comment, index) => (
          <CommentComponent 
            key={index}
            comment={comment} 
            onUpdate={(commentId, updatedContent) =>
            updateComment(boardId, commentId, updatedContent)}
            onDelete={handleDelete} 
          />
        ))
      }
    </S.CommentWrapper>
  );
};
