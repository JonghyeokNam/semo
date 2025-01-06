import { create } from "zustand";
import { API } from "../lib/apis/utils/index"; 

export const useNewCheckStore = create((set) => ({
  isNewUser: null, // 신규 유저 여부 초기값
  checkNewUser: async () => {
    try {
      const response = await API.get("/users/check"); // axios 인스턴스 사용
      const isNew = response.data.result; // API 응답에서 신규 유저 여부 확인
      set({ isNewUser: isNew }); // 상태 업데이트
      return isNew;
    } catch (error) {
      console.error("Failed to check user status:", error);
      throw error;
    }
  },
  resetNewUserStatus: () => set({ isNewUser: null }), // 상태 초기화
}));

