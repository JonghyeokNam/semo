import React, { useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import {useAuthStore} from "../../store/useAuthStore";
import {useNewCheckStore} from "../../store/useNewCheckStore";

function OAuth2RedirectHandler() {
  const navigate = useNavigate();
  const location = useLocation();
  const { storeLogin, fetchUserInfo } = useAuthStore();
  const { checkNewUser } = useNewCheckStore();

  useEffect(() => {
    const handleLogin = async () => {
      const queryParams = new URLSearchParams(location.search);
      const token = queryParams.get("token"); // URL에서 'token' 파라미터 추출

      if (token) {

        try {
        
          // 로그인 상태 업데이트
          storeLogin(token, null); // isLoggedIn : true 설정

          // 신규 유저 여부 확인
          const isNewUser = await checkNewUser();
          console.log(isNewUser);

          // 유저 정보 가져오기
          await fetchUserInfo();

          // 신규 유저 여부에 따라 리디렉션
          if (isNewUser) {
            navigate("/signup"); // 추가 정보 입력 페이지로 이동
          } else {
            navigate("/"); // 홈 페이지로 이동
          }
        } catch (error) {
          console.error("Failed to process login flow:", error);
          navigate("/error"); 
        }
      } else {
        console.error("Access token not found in the URL");
      }
    };

    handleLogin();
  }, [location, navigate, storeLogin, fetchUserInfo, checkNewUser]);

  return <div>카카오 로그인 중...</div>;
}

export default OAuth2RedirectHandler;
