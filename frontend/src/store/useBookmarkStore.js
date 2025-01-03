import { create } from 'zustand';
import { API } from '../lib/apis/utils/index';

export const useGetMyBookmarksStore = create((set) => ({
    bookmarkList: [], // 데이터를 저장할 상태
    loading: false, // 로딩 상태
    error: null, // 에러 상태
  
    // 목록을 가져오는 함수
    fetchBookmarkList: async () => {
      set({ loading: true, error: null }); // 로딩 상태 설정
      try {
        const response = await API.get(`/bookmarks`);
        if (response.data.resultCode === 'SUCCESS') {
          set({ bookmarkList: response.data.result, loading: false });
        } else {
          throw new Error('Failed to fetch list');
        }
      } catch (error) {
        set({ error: error.message, loading: false });
      }
    },
  }));