import { create } from 'zustand';
import { API } from '../lib/apis/utils/index';

// 알림 목록 조회회
const useGetNotificationsStore = create((set) => ({
  list: [], // 데이터를 저장할 상태
  loading: false, // 로딩 상태
  error: null, // 에러 상태

  // 알림 목록을 가져오는 함수
  fetchList: async () => {
    set({ loading: true, error: null }); // 로딩 상태 설정
    try {
      const response = await API.get(`/notifications`);
      if (response.data.resultCode === 'SUCCESS') {
        set({ list: response.data.result, loading: false });
      } else {
        throw new Error('Failed to fetch list');
      }
    } catch (error) {
      set({ error: error.message, loading: false });
    }
  },
}));

export { useGetNotificationsStore };
