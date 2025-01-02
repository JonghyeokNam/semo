import { create } from "zustand";
import { API } from "../lib/apis/utils/index"; // API import

// 지원폼 모달 데이터 post 요청 
const useApplyStore = create((set) => ({
  isLoading: false,
  isError: false,
  error: null,
  submitApplication: async (boardId, positionId, aboutMe, userId) => {
    set({ isLoading: true, isError: false, error: null }); // 요청 시작

    try {
      const response = await API.post(`/boards/${boardId}/applyform`,{
        positionId: positionId+1,
        aboutMe: aboutMe,
      },
      {
        params: { userId: userId } // userId를 쿼리 파라미터로 추가
    }
    ); 

      console.log(response);
      
      if (response.status === 200) {
        set({ isLoading: false }); // 요청 성공
        console.log("API success: ", response);
      }
    } catch (error) {
      set({ isLoading: false, isError: true, error: error.response ? error.response.data : error.message });
      console.error(error);
    }
  },
}));

export default useApplyStore;
