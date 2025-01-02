import React, { useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";

function OAuth2RedirectHandler() {
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const queryParams = new URLSearchParams(location.search);
    const token = queryParams.get('token'); // URL에서 'token' 파라미터 추출
    console.log(token);

    if (token) {
      // 액세스 토큰을 localStorage에 저장
      localStorage.setItem("access_token", token);
      navigate("/"); // 홈 페이지로 이동
    } else {
      console.error("Access token not found in the URL");
    }
  }, [location, navigate]);

  return <div>카카오 로그인 중...</div>;
}

export default OAuth2RedirectHandler;
