import React, { useEffect, useState } from "react";
import * as S from "./style";
import Container from "./container";
import SelectComponent from "../../components/ui/selectComponent";
import BoardListWrite from "../../components/ui/board/boardListWrite";
import BoardListApply from "../../components/ui/board/boardListApply";
import BoardListBook from "../../components/ui/board/boardListBook";
import {useAuthStore, useUpdateUserStore } from "../../store/useAuthStore";
import { useGetMyBookmarksStore } from "../../store/useBookmarkStore";
import { useGetMyBoardsStore } from "../../store/useBoardStore";
import { useGetUserApplyFormsStore } from "../../store/useApplyStore";


const positionList = [
  { value: "designer", label: "UI/UX" },
  { value: "frontend", label: "프론트엔드 개발자" },
  { value: "backend", label: "백엔드 개발자" },
  { value: "marketer", label: "마케터" },
];

const MyPage = () => {
  const { user, fetchUserInfo } = useAuthStore();
  const { boardList, fetchBoardList } = useGetMyBoardsStore();
  const { bookmarkList, fetchBookmarkList } = useGetMyBookmarksStore();
  const { updateUserInfo, isUpdating } = useUpdateUserStore();
  const { applyForms, fetchUserApplyForms } = useGetUserApplyFormsStore();
  const [selectedPosition, setSelectedPosition] = useState(user.position || "");

  useEffect(() => {
    fetchUserInfo();
    fetchBoardList();
    fetchBookmarkList();
    fetchUserApplyForms();
  }, [fetchUserInfo, fetchBoardList, fetchBookmarkList])

  useEffect(() => {
    if (user?.position) {
      setSelectedPosition(user.position);
    }
  }, [user]);

  const handlePositionChange = (selectedOption) => {
    setSelectedPosition(selectedOption?.value || "");
  };

  const handleSave = async () => {
    const userInfo = {
      userEmail: user.userEmail, // 사용자 이메일
      position: selectedPosition, // 선택된 포지션
      campusName: user.campusName, // 캠퍼스 이름
      courseName: user.courseName, // 교육 과정 이름
    };

    try {
      await updateUserInfo(userInfo); // 사용자 정보 업데이트 요청
      fetchUserInfo(); // 최신 사용자 정보 가져오기
    } catch (error) {
      console.error("프로필 저장 중 오류 발생:", error);
    }
  };

  return (
    <S.mypageWrapper>
      <S.MainTitle>마이 페이지</S.MainTitle>
      
      <Container title="기본 정보">
        <S.Row>
          <S.Column>
            <S.ProfileImage src="/img/sesacHi.png" alt="Profile" />
            <S.WelcomeText>{user.username}님 환영해요 :) </S.WelcomeText>
          </S.Column>
          <S.Column>
            <S.BoxColumn>
            <S.BoxTitle>소속 캠퍼스</S.BoxTitle>
              <S.Box>
                <S.BoxContent>{user.campusName}</S.BoxContent>
              </S.Box>
            </S.BoxColumn>
            <S.BoxColumn>
            <S.BoxTitle>교육과정</S.BoxTitle>
              <S.Box>
                <S.BoxContent>{user.courseName}</S.BoxContent>
              </S.Box>
            </S.BoxColumn>
            <SelectComponent
              label="포지션"
              options={positionList}
              placeholder="포지션 선택"
              width="310px"
              value={selectedPosition}
              onChange={handlePositionChange}
            />
            <S.SaveButton onClick={handleSave} disabled={isUpdating}>프로필 저장</S.SaveButton>
          </S.Column>
        </S.Row>

      </Container>

      <Container title="작성글" type="작성">
        <S.ContainerBody >
          {boardList.map((item, index) => (
            <BoardListWrite key={index} boardData={item}/>
          ))}
        </S.ContainerBody>
      </Container>
      <Container title="참여글" type="참여" >
      <S.ContainerBody >
        {applyForms.map((item, index) => (
          <BoardListApply boardData={item.board} formData={item} key={index}/>
        ))}
      </S.ContainerBody>
      </Container>
      <Container title="북마크" type="북마크" >
        <S.ContainerBody >
          {bookmarkList.map((item, index) => (
            <BoardListBook boardData={item} key={index} />
          ))}
        </S.ContainerBody>
      </Container>
    </S.mypageWrapper>
  );
};

export default MyPage;
