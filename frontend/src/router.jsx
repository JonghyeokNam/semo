import { createBrowserRouter } from "react-router-dom";
import App from "./App";
import Home from "./pages/home/index";
import Chat from "./pages/chat/index";
import Login from "./pages/login/index";
import Signup from "./pages/login/signup";
import Mypage from "./pages/mypage/main/index";
import ApplyList from "./pages/applylist/index";
import BoardDetail from "./pages/board/detail/index";
import BoardModify from "./pages/board/modify/index";
import BoardWrite from "./pages/board/write/index";
import NotFound from "./NotFound";


const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      // 홈 페이지
      { path: "/", element: <Home /> },

      // 채팅 페이지
      { path: "/chat", element: <Chat /> },

      // 로그인 페이지
      { path: "/login", element: <Login /> },

      // 회원가입 페이지
      { path: "/signup", element: <Signup /> },

      // 마이 페이지
      { path: "/mypage", element: <Mypage /> },

      // 지원자 리스트
      { path: "/applylist", element: <ApplyList /> },

      // 지원자 리스트
      { path: "/board/detail", element: <BoardDetail /> },

      // 지원자 리스트
      { path: "/board/modify", element: <BoardModify /> },

      // 지원자 리스트
      { path: "/board/write", element: <BoardWrite /> },

    ],
    errorElement: <NotFound />,
  },
]);

export default router;
