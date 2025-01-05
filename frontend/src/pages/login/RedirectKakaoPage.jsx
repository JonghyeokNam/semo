// RedirectKakaoPage.jsx
import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const RedirectKakaoPage = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const params = new URL(window.location.href).searchParams;
    const code = params.get('code');
    const REST_API_KEY = process.env.REACT_APP_REST_API_KEY;
    const REDIRECT_URI = process.env.REACT_APP_REDIRECT_URI;
    const CLIENT_SECRET = process.env.REACT_APP_CLIENT_SECRET;

    const link = `https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&code=${code}&client_secret=${CLIENT_SECRET}`;

    axios.post(link)
      .then((response) => {
        const accessToken = response.data.access_token;
        // 액세스 토큰을 사용하여 사용자 정보를 요청
        axios.get("https://kapi.kakao.com/v2/user/me", {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
          .then((userResponse) => {
            const user = userResponse.data;
            // 사용자 정보로 DB에서 회원 여부 확인
            checkUserAndRedirect(user);
          })
          .catch((error) => {
            console.error("카카오 사용자 정보 요청 오류:", error);
          });
      })
      .catch((error) => {
        console.error("카카오 토큰 요청 오류:", error);
      });
  }, [navigate]);

  const checkUserAndRedirect = (user) => {
    // 서버에 유저 정보 전달 (예: DB에서 확인)
    axios.post("/api/check-user", user)
      .then((response) => {
        if (response.data.exists) {
          // 유저가 존재하면 메인 페이지로 이동
          navigate("/");
        } else {
          // 유저가 없으면 회원가입 페이지로 이동
          navigate("/signup");
        }
      })
      .catch((error) => {
        console.error("유저 확인 오류:", error);
      });
  };

  return <div>로그인 중...</div>;
};

export default RedirectKakaoPage;
