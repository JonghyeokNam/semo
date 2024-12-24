import React, { useState, useRef } from 'react';
import * as S from "./style";
import Editor from './quill';
import Quill from 'quill';
import SelectComponent from '../../../components/ui/selectComponent';
import CustomCalendar from '../../../components/ui/calendar';

const Delta = Quill.import('delta');

const Index = () => {
  const recruitmentTypeData = [
    { value: "project", label: "프로젝트" },
    { value: "study", label: "스터디" },
  ];
  const participantCountData = [
    { value: "undefined", label: "인원 미정" },
    { value: "1", label: "1명" },
    { value: "2", label: "2명" },
    { value: "3", label: "3명" },
    { value: "4", label: "4명" },
    { value: "5", label: "5명" },
    { value: "6", label: "6명" },
    { value: "7", label: "7명" },
    { value: "8", label: "8명" },
    { value: "9", label: "9명" },
    { value: "10+", label: "10명 이상" },
  ];
  const processMethodData = [
    { value: "online", label: "온라인" },
    { value: "offline", label: "오프라인" },
    { value: "both", label: "온/오프라인" },
  ];
  const periodData = [
    { value: "undefined", label: "기간 미정" },
    { value: "1", label: "1개월" },
    { value: "2", label: "2개월" },
    { value: "3", label: "3개월" },
    { value: "4", label: "4개월" },
    { value: "5", label: "5개월" },
    { value: "6+", label: "6개월 이상" },
  ];
  const deadlineData = [
    { value: "1w", label: "1주" },
    { value: "2w", label: "2주" },
    { value: "1m", label: "1개월" },
  ];

  const positionData = [
    { value: "uiux", label: "UI/UX" },
    { value: "front", label: "프론트엔드 개발자" },
    { value: "back", label: "백엔드 개발자" },
    { value: "marketer", label: "마케터" },
  ];

  const [range, setRange] = useState();
  const [lastChange, setLastChange] = useState();
  const [readOnly, setReadOnly] = useState(false);

  const quillRef = useRef();

  return (
    <S.Container>
      <S.FormSection>
        <S.title>1. 프로젝트 기본 정보를 입력해주세요.</S.title>
        <S.Separator />
        <S.Row>
          <S.Column>
            <SelectComponent
              label="모집 구분"
              options={recruitmentTypeData}
               placeholder="스터디 / 프로젝트"
            />
          </S.Column>
          <S.Column>
            <SelectComponent
              label="모집 인원"
              options={participantCountData}
               placeholder="인원 미정 ~ 10명 이상"
            />
          </S.Column>
          <S.Column>
            <SelectComponent
              label="진행 방식"
              options={processMethodData}
              placeholder="온라인 / 오프라인"
            />
          </S.Column>
        </S.Row>
        <S.Row>
          <S.Column>
            <SelectComponent
              label="진행 기간"
              options={periodData}
              placeholder="기간 미정 ~ 6개월 이상"
            />
          </S.Column>
          <S.Column>
            <S.Label>모집 마감일</S.Label>
            <CustomCalendar
              onChange={(selectedDate) => {
                console.log("Selected date:", selectedDate);
              }}
            />
          </S.Column>
          <S.Column>
          <SelectComponent
            label="모집 포지션"
            options={positionData}
            isMulti={true}
            placeholder="프론트엔드, 백엔드..."
          />
          </S.Column>
        </S.Row>
      </S.FormSection>
      <S.FormSection>
        <S.title>2. 프로젝트에 대해 소개해주세요.</S.title>
        <S.Separator />
        <S.Column style={{ width: "100%" }}>
          <S.Label>제목</S.Label>
          <S.TextInput placeholder="글 제목을 입력해주세요." />
          <S.EditorContainer>
            <Editor
              ref={quillRef}
              readOnly={readOnly}
              defaultValue={new Delta().insert('내용을 작성해주세요.\n')}
              onSelectionChange={setRange}
              onTextChange={setLastChange}
              style={{ minHeight: '200px' }}
            />
          </S.EditorContainer>
          <S.ButtonContainer>
            <S.CancelButton>취소</S.CancelButton>
            <S.SubmitButton>등록하기</S.SubmitButton>
          </S.ButtonContainer>
        </S.Column>
      </S.FormSection>
    </S.Container>
  );
};

export default Index;
