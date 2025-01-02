import axios from "axios";

export const API = axios.create({
  baseURL: process.env.REACT_APP_API_URL, // 환경 변수에서 API URL 가져오기
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true, // 쿠키를 포함한 요청
});

// 요청 인터셉터 추가
API.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("access_token"); // localStorage에서 토큰 가져오기
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`; // Authorization 헤더 추가
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
