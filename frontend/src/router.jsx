import { createBrowserRouter } from "react-router-dom";
import App from "./App";
import Home from "./pages/home/index";
import Chat from "./pages/chat/index";
import Login from "./pages/login/index";
import Signup from "./pages/login/signup";
import OAuth2RedirectHandeler from "./pages/login/OAuth2RedirectHandler";
import Mypage from "./pages/mypage/index";
import ApplyList from "./pages/applylist/index";
import BoardDetail from "./pages/board/detail/index";
import BoardModify from "./pages/board/modify/index";
import BoardWrite from "./pages/board/write/index";
import NotFound from "./NotFound";
import ProtectedRoute from "./components/ProtectedRoute";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      // 홈 페이지
      { path: "/", element: <ProtectedRoute><Home /></ProtectedRoute> },

      // 채팅 페이지
      { path: "/chat", element: <ProtectedRoute><Chat /></ProtectedRoute> },

      // 로그인 페이지 (보호 필요 없음)
      { path: "/login", element: <Login /> },

      // 회원가입 페이지 (보호 필요 없음)
      { path: "/signup", element: <Signup /> },

      // 카카오 OAuth 리다이렉트 핸들러 (보호 필요 없음)
      { path: "/login/oauth2/code/kakao", element: <OAuth2RedirectHandeler /> },

      // 마이 페이지
      { path: "/mypage", element: <ProtectedRoute><Mypage /></ProtectedRoute> },

      // 지원자 리스트
      { path: "/applylist", element: <ProtectedRoute><ApplyList /></ProtectedRoute> },

      // 게시글 상세 페이지
      { path: "/boards/:boardId", element: <BoardDetail /> },

      // 게시글 수정 페이지
      { path: "/board/modify", element: <ProtectedRoute><BoardModify /></ProtectedRoute> },

      // 게시글 작성 페이지
      { path: "/board/write", element: <ProtectedRoute><BoardWrite /></ProtectedRoute> },
    ],
    errorElement: <NotFound />,
  },
]);

export default router;
