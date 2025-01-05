import { create } from 'zustand';
import { API } from '../lib/apis/utils/index';

export const useGetMyBoardsStore = create((set) => ({
  boardList: [], // 데이터를 저장할 상태
  loading: false, // 로딩 상태
  error: null, // 에러 상태

  // 목록을 가져오는 함수
  fetchBoardList: async () => {
    set({ loading: true, error: null }); // 로딩 상태 설정
    try {
      const response = await API.get(`/boards/myboards`);
      if (response.data.resultCode === 'SUCCESS') {
        set({ boardList: response.data.result, loading: false });
      } else {
        throw new Error('Failed to fetch list');
      }
    } catch (error) {
      set({ error: error.message, loading: false });
    }
  },
}));

export const useGetBoardDetailStore = create((set) => ({
  boardInfo: [], // 데이터를 저장할 상태
  loading: false, // 로딩 상태
  error: null, // 에러 상태

  // 목록을 가져오는 함수
  fetchBoardInfo: async (boardId) => {
    set({ loading: true, error: null }); // 로딩 상태 설정
    try {
      const response = await API.get(`/boards/${boardId}`);
      if (response.data.resultCode === 'SUCCESS') {
        set({ 
          boardInfo: response.data.result, 
          loading: false 
        });
      } else {
        throw new Error('Failed to fetch list');
      }
    } catch (error) {
      set({ error: error.message, loading: false });
    }
  },

  // 모집 분야 업데이트
  updateRecruitmentTypes: (types) =>
    set((state) => ({
      boardInfo: {
        ...state.boardInfo,
        recruitmentTypes: types, // 배열 형태로 업데이트
      },
    })),
  }));
    
  export const useCreateBoardStore = create((set) => ({
    boardData: null, // 생성된 게시글 데이터를 저장할 상태
    loading: false, // 로딩 상태
    error: null, // 에러 상태
  
    // 게시글 생성 함수
    createBoard: async (payload) => {
      set({ loading: true, error: null }); // 로딩 상태 시작
      try {
        const response = await API.post('/boards', payload);
        if (response.data.resultCode === 'SUCCESS') {
          set({ 
            boardData: response.data.result, // 생성된 게시글 데이터 저장
            loading: false 
          });
        } else {
          throw new Error('Failed to create board');
        }
      } catch (error) {
        set({ error: error.message, loading: false }); // 에러 상태 저장
      }
    },
  }));