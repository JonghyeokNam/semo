import { create } from 'zustand';
import { API } from '../lib/apis/utils/index';

// 주현우
const useBoardStore = create((set) => ({
  // 상태
  boards: [], // 게시글 목록
  totalItems: 0, // 전체 게시글 수
  totalPages: 0, // 전체 페이지 수
  currentPage: 1, // 현재 페이지
  pageSize: 10, // 페이지당 게시글 수
  loading: false, // 로딩 상태
  error: null, // 에러 상태

  // 게시물 목록 불러오기
  fetchBoards: async (page = 1, size = 10) => {
    set({ loading: true, error: null }); // 로딩 시작
    try {
      const response = await API.get(`/boards?page=${page}&size=${size}`);
      if (response.data.resultCode === 'SUCCESS') {
        const { content, totalElements, totalPages } = response.data.result; // API 응답 데이터 구조에 맞게 추출
        set({
          boards: content,
          totalItems: totalElements,
          totalPages: totalPages,
          currentPage: page,
          loading: false, // 로딩 종료
        });
      }
    } catch (err) {
      set({ error: err.message, loading: false });
    }
  },
}));

export default useBoardStore;
