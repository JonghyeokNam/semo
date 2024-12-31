import { create } from "zustand";
import { API } from "../lib/apis/utils/index"; // API import

// 지원폼 모달 데이터 post 요청 
const useApplyStore = create((set) => ({
  isLoading: false,
  isError: false,
  error: null,
  submitApplication: async (boardId, applicationData) => {
    set({ isLoading: true, isError: false, error: null }); // 요청 시작

    try {
      const response = await API.post(`/boards/${boardId}/applyform`, applicationData); // POST 요청
      if (response.status === 200) {
        set({ isLoading: false }); // 요청 성공
      }
    } catch (error) {
      set({ isLoading: false, isError: true, error: error.message }); // 요청 실패
    }
  },
}));

export default useApplyStore;
