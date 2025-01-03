import { create } from 'zustand';
import { API } from "../lib/apis/utils/index"; 

export const useAuthStore = create((set) => ({
  isLoggedIn: localStorage.getItem("access_token") ? true : false, // 초기 로그인 상태
  user: {}, // 초기 사용자 정보
  storeLogin: (token, user) => {
    localStorage.setItem("access_token", token); // 토큰 저장
    set({ isLoggedIn: true, user }); // 로그인 상태와 사용자 정보 업데이트
  },
  storeLogout: () => {
    localStorage.removeItem("access_token"); // 토큰 삭제
    set({ isLoggedIn: false, user: null }); // 로그아웃 상태로 변경
  },
  fetchUserInfo: async () => {
    try {
      const response = await API.get('/users'); // API 모듈을 사용하여 사용자 정보 요청
      if (response.data.resultCode === "SUCCESS") {
        set({ user: response.data.result }); // 사용자 정보 저장
      } else {
        console.error('사용자 정보를 가져오는 데 실패했습니다.');
      }
    } catch (error) {
      console.error('API 요청 중 오류 발생:', error);
    }
  },
}));
