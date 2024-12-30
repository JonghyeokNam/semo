import React, { useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function OAuth2RedirectHandler() {
  const navigate = useNavigate();
  const code = new URL(window.location.href).searchParams.get("code");
  const grantType = "authorization_code";
  const REST_API_KEY = process.env.REACT_APP_REST_API_KEY;
  const REDIRECT_URI = process.env.REACT_APP_REDIRECT_URI;

  useEffect(() => {
    if (code) {
      axios
        .post(
          `https://kauth.kakao.com/oauth/token?grant_type=${grantType}&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&code=${code}`,
          {},
          {
            headers: {
              "Content-type": "application/x-www-form-urlencoded;charset=utf-8",
            },
          }
        )
        .then((res) => {
          const { access_token } = res.data;
          axios
            .post(
              `https://kapi.kakao.com/v2/user/me`,
              {},
              {
                headers: {
                  Authorization: `Bearer ${access_token}`,
                  "Content-type": "application/x-www-form-urlencoded;charset=utf-8",
                },
              }
            )
            .then((userRes) => {
              localStorage.setItem("user", JSON.stringify(userRes.data));
              navigate("/"); // 홈 화면으로 네비게이션
            })
            .catch((userError) => {
              console.error("User Info Error: ", userError);
            });
        })
        .catch((error) => {
          console.error("Token Request Error: ", error);
        });
    }
  }, [code, navigate]); // navigate와 code만 의존성 배열에 추가

  return <div>카카오 로그인 중...</div>;
}

export default OAuth2RedirectHandler;
