import axios from "axios";

// 이유진
// axios 인스턴스 생성
export const API = axios.create({
  baseURL: process.env.REACT_APP_API_URL, // 환경 변수에서 API URL 가져오기
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true, // 쿠키를 포함한 요청
});

let isRefreshing = false; // 토큰 갱신 상태 플래그
let failedRequestQueue = []; // 실패한 요청을 큐에 저장

// 요청 인터셉터 추가
// interceptor는 요청 또는 응답을 가로채어 추가적인 처리 => 인증 토큰 설정, 오류 처리 등을 중앙에서 관리
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

// 토큰 갱신 함수
const refreshToken = async () => {
  try {
    const response = await API.post('/tokens'); // 토큰 갱신 요청

    // 서버가 헤더에서 새 토큰을 전송했다고 가정
    const newToken = response.headers['authorization']; // 헤더에서 새 토큰 추출
    if (newToken) {
      localStorage.setItem('access_token', newToken); 
      return newToken; // 새로운 액세스 토큰 반환
    } else {
      throw new Error('새로운 토큰을 헤더에서 찾을 수 없습니다.');
    }
  } catch (e) {
    // 토큰 갱신 실패 시 로그인 페이지로 리디렉션하거나 에러 처리
    localStorage.removeItem('access_token');
    window.location.href = '/login'; // 로그인 페이지로 리디렉션
    return Promise.reject(e);
  }
};

// 401 오류가 발생한 경우 토큰 갱신 후 요청 재시도
const refreshTokenAndRetry = (error) => {
  const { response: errorResponse } = error;

  if (!isRefreshing) {
    isRefreshing = true;

    return refreshToken()
      .then((newToken) => {
        // 새로운 토큰으로 실패한 요청을 재시도
        failedRequestQueue.forEach((request) => request(newToken));
        failedRequestQueue = []; // 큐 초기화

        // 요청 헤더에 새로운 토큰을 추가하고 재시도
        errorResponse.config.headers["Authorization"] = `Bearer ${newToken}`;

        return API(errorResponse.config); // 원래의 요청을 새로운 토큰으로 재시도
      })
      .finally(() => {
        isRefreshing = false;
      });
  } else {
    // 이미 토큰 갱신 중이라면 요청을 큐에 추가
    return new Promise((resolve, reject) => {
      failedRequestQueue.push((newToken) => {
        errorResponse.config.headers["Authorization"] = `Bearer ${newToken}`;
        resolve(API(errorResponse.config)); // 재시도 요청
      });
    });
  }
};

// 응답 인터셉터 추가
API.interceptors.response.use(
  (response) => {
    return response; // 정상 응답 처리
  },
  (error) => {
    const { response } = error;
    if (response && response.status === 401) {
      return refreshTokenAndRetry(error); // 401 오류 발생 시 토큰 갱신 후 재시도
    }
    return Promise.reject(error); // 401 이외의 오류는 그대로 전달
  }
);

export default API;
