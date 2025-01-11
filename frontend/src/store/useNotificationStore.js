import { create } from 'zustand';
import { API } from '../lib/apis/utils/index';

// 차현철
// 알림 목록 조회
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

// 읽지 않은 알름 확인
const useCheckNoReadNotificationStore = create((set) => ({
  isReadAll: true,

  fechIsReadAll: async () => {
    set({loading: true, error: null });
    try {
      const response = await API.get(`/notifications/check`);
      if (response.data.resultCode === 'SUCCESS') {
        set({isReadAll: response.data.result, loading: false });
      } else {
        throw new Error('Failed to fetch check')
      }
    } catch (error) {
      set({ error: error.message, loading: false })
    }
  },
}))

// 알림 읽음 처리
const useReadNotificationStore = create((set) => ({
  readNotification: async (notificationId) => {
    try {
      const response = await API.put(`/notifications/${notificationId}`);
      if (response.data.resultCode === 'SUCCESS') {
        console.log(`Notification ${notificationId} marked as read.`);
      } else {
        throw new Error('Failed to mark notification as read');
      }
    } catch (error) {
      console.error(`Error marking notification as read: ${error.message}`);
    }
  },
}))

export { useGetNotificationsStore, useCheckNoReadNotificationStore, useReadNotificationStore };
