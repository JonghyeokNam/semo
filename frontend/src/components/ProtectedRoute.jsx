import { Navigate } from "react-router-dom";
import { useNewCheckStore } from "../store/useNewCheckStore";
import { useAuthStore } from "../store/useAuthStore";

const ProtectedRoute = ({ children }) => {
  const { isNewUser } = useNewCheckStore(); // 추가정보 여부 확인
  const { isLoggedIn } = useAuthStore(); // 로그인 상태 확인
  const hasCookie = !!localStorage.getItem("access_token"); // access_token 존재 여부 확인

  // 로그인되지 않은 경우
  if (!isLoggedIn) {
    alert("로그인을 먼저 해주세요.");
    return <Navigate to="/login" replace />;
  }

  // 추가정보 입력이 필요한 경우
  if (isNewUser) {
    alert("추가정보를 입력해주세요.");
    return <Navigate to="/signup" replace />;
  }

  // 세션 만료 또는 쿠키가 없는 경우
  if (!hasCookie) {
    alert("로그인 세션이 만료되었습니다.");
    return <Navigate to="/login" replace />;
  }

  return children; // 모든 조건이 충족되면 페이지 렌더링
};

export default ProtectedRoute;
