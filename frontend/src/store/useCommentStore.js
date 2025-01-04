import { create } from "zustand";
import { API } from "../lib/apis/utils/index";

export const useCommentStore = create((set, get) => ({
  comments: [], 
  loading: false,
  error: null, 

  // 상태 초기화 함수
  setLoading: (loading) => set({ loading }),
  setError: (error) => set({ error }),

  // 댓글 목록 조회 API
  fetchComments: async (boardId) => {
    get().setLoading(true);
    get().setError(null);
    try {
      const response = await API.get(`/boards/${boardId}/comments`);
      if (response.data.resultCode === "SUCCESS") {
        set({ comments: response.data.result, loading: false });
      } else {
        throw new Error("댓글 목록 불러오기 실패");
      }
    } catch (error) {
      console.error("댓글 목록 조회 오류:", error);
      get().setError(error.message);
    } finally {
      get().setLoading(false);
    }
  },

  // 댓글 등록 API
  addComment: async (boardId, content) => {
    get().setLoading(true);
    get().setError(null);
    try {
        const response = await API.post(`/boards/${boardId}/comments`, {content,});
      if (response.data.resultCode === "SUCCESS") {
        await get().fetchComments(boardId);
      } else {
        throw new Error("댓글 목록 불러오기 실패");
      }
    } catch (error) {
      console.error("댓글 추가 오류:", error);
      get().setError(error.message);
      throw error;
    } finally {
      get().setLoading(false);
    }
  },


  // 댓글 수정 API
  updateComment: async (boardId, commentId, content) => {
    get().setLoading(true);
    get().setError(null);
    try {
      const response = await API.put(`/comments/${commentId}`, { content });
      if (response.data.resultCode === "SUCCESS") {
        await get().fetchComments(boardId); // 목록 새로고침
      } else {
        throw new Error("댓글 수정 실패");
      }
    } catch (error) {
      console.error("댓글 수정 오류:", error);
      get().setError(error.message);
      throw error;
    } finally {
      get().setLoading(false);
    }
  },

    // 댓글 삭제 API
    deleteComment: async (boardId, commentId) => {
        get().setLoading(true);
        get().setError(null);
        try {
          const response = await API.delete(`/comments/${commentId}`);
          if (response.data.resultCode === "SUCCESS") {
            await get().fetchComments(boardId); // 삭제 후 댓글 목록 갱신
          } else {
            throw new Error("댓글 삭제 실패");
          }
        } catch (error) {
          console.error("댓글 삭제 오류:", error);
          get().setError(error.message);
          throw error;
        } finally {
          get().setLoading(false);
        }
      },
}));
