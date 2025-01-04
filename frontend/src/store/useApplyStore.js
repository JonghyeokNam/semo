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

export const useGetUserApplyFormsStore = create((set) => ({
  applyForms: [], // 지원 폼 데이터 상태
  isLoading: false, // 로딩 상태
  isError: false, // 에러 상태
  error: null, // 에러 메시지

  // 지원 폼 데이터를 가져오는 함수
  fetchUserApplyForms: async () => {
    set({ isLoading: true, isError: false, error: null }); // 로딩 상태 설정

    try {
      const response = await API.get("/user/applyforms");

      if (response.status === 200) {
        set({ applyForms: response.data.result, isLoading: false }); // 성공적으로 데이터 저장
      } else {
        throw new Error("Failed to fetch apply forms.");
      }
    } catch (error) {
      set({ 
        isLoading: false, 
        isError: true, 
        error: error.response ? error.response.data : error.message 
      }); // 에러 처리
      console.error("지원 폼 데이터를 가져오는 중 오류 발생:", error);
    }
  },
}));

export const useUpdateApplyFormStore = create((set) => ({
  isUpdating: false, // 수정 중 상태
  isError: false, // 에러 발생 여부
  error: null, // 에러 메시지
  updateApplyForm: async (applyFormId, positionName, aboutMe) => {
    set({ isUpdating: true, isError: false, error: null });

    try {
      const response = await API.put(`/user/applyforms/${applyFormId}`, {
        positionName,
        aboutMe,
      });

      if (response.status === 200) {
        set({ isUpdating: false });
      }
    } catch (error) {
      set({
        isUpdating: false,
        isError: true,
        error: error.response ? error.response.data : error.message,
      });
      console.error("신청서 수정 중 오류:", error);
    }
  },
}));

export default useApplyStore;
