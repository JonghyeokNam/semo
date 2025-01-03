import { Navigate } from "react-router-dom";
import { useNewCheckStore } from "../store/useNewCheckStore";
import { useAuthStore } from "../store/useAuthStore";


const ProtectedRoute = ({ children }) => {
  const { isNewUser } = useNewCheckStore();
  const { isLoggedIn } = useAuthStore();
  const hasCookie = !!localStorage.getItem("access_token"); // 쿠키 상태 확인

  if (!isLoggedIn || isNewUser || !hasCookie) {
    alert("로그인을 먼저 해주세요.");
    // isNewUser가 true이거나 쿠키가 없으면 로그인 페이지로 리디렉션
    return <Navigate to="/login" replace />;
  }

  return children; // 권한이 있는 경우 페이지 렌더링
};

export default ProtectedRoute;
