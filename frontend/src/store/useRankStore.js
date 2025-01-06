import { create } from 'zustand';
import { API } from '../lib/apis/utils/index';

// 모집이 활발한 캠퍼스 랭킹
const useRecruitRankStore = create((set) => ({
  ranks: [], // 랭킹 데이터를 저장할 상태
  loading: false, // 로딩 상태
  error: null, // 에러 상태

  // 랭킹 데이터를 가져오는 함수
  fetchRanks: async () => {
    set({ loading: true, error: null }); // 로딩 상태 설정
    try {
      const year = new Date().getFullYear(); // 현재 년도 가져오기
      const response = await API.get(`/campuses/recruit/${year}`);
      if (response.data.resultCode === 'SUCCESS') {
        set({ ranks: response.data.result, loading: false });
      } else {
        throw new Error('Failed to fetch ranks');
      }
    } catch (error) {
      set({ error: error.message, loading: false });
    }
  },
}));

// 참여가 활발한 캠퍼스 랭킹
const useActRankStore = create((set) => ({
  ranks: [], // 랭킹 데이터를 저장할 상태
  loading: false, // 로딩 상태
  error: null, // 에러 상태

  // 랭킹 데이터를 가져오는 함수
  fetchRanks: async () => {
    set({ loading: true, error: null }); // 로딩 상태 설정
    try {
      const year = new Date().getFullYear(); // 현재 년도 가져오기
      const response = await API.get(`/campuses/act/${year}`);
      if (response.data.resultCode === 'SUCCESS') {
        set({ ranks: response.data.result, loading: false });
      } else {
        throw new Error('Failed to fetch ranks');
      }
    } catch (error) {
      set({ error: error.message, loading: false });
    }
  },
}));

export { useRecruitRankStore, useActRankStore };
