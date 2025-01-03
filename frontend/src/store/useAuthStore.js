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

export const useUpdateUserStore = create((set) => ({
  isUpdating: false, // 업데이트 상태
  updateError: null, // 업데이트 실패 시 에러 메시지
  updateUserInfo: async (userInfo) => {
    set({ isUpdating: true, updateError: null }); // 업데이트 시작 상태 설정
    try {
      const response = await API.put('/users', userInfo); // 사용자 정보 업데이트 요청
      if (response.data.resultCode === "SUCCESS") {
        set({ isUpdating: false }); // 업데이트 성공 시 상태 초기화
      } else {
        set({ isUpdating: false, updateError: "업데이트 실패" });
        console.error("사용자 정보 업데이트 실패:", response.data.message);
      }
    } catch (error) {
      set({ isUpdating: false, updateError: error.message });
      console.error("사용자 정보 업데이트 중 오류 발생:", error);
    }
  },
}));