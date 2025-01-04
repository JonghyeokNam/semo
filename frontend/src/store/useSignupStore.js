import { create } from 'zustand';
import { API } from '../lib/apis/utils/index'; 

const useSignupStore = create((set) => ({
    campuses: [], // This is already correct; ensure it's always an array
    courses: [],
    loading: false,
  
    fetchCampuses: async () => {
      set({ loading: true });
      try {
        const response = await API.get('/campuses');
        if (response.data.resultCode === 'SUCCESS') {
          set({ campuses:response.data.result});
        }
      } catch (error) {
        console.error('캠퍼스를 가져오는 데 실패했습니다', error);
        set({ campuses: [] }); // Fallback to an empty array if the API call fails
      } finally {
        set({ loading: false });
      }
    },
  
    fetchCourses: async (campusId) => {
      set({ loading: true });
      try {
        const response = await API.get(`/campuses/${campusId}/courses`);
        if (response.data.resultCode === 'SUCCESS') {
          set({ courses: response.data.result });
        }
      } catch (error) {
        console.error('과정을 가져오는 데 실패했습니다', error);
      } finally {
        set({ loading: false });
      }
    },
  }));
  
  export default useSignupStore;
  