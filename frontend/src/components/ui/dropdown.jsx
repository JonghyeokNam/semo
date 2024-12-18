import React, { useState, useEffect, useRef } from "react";
import * as S from "./dropdownStyle"; // 스타일 파일 import

export const Dropdown = (props) => {
  const list = props.props.data; // 드롭다운 데이터 리스트
  const selectRef = useRef(null); // 드롭다운 요소에 대한 ref
  const [currentValue, setCurrentValue] = useState(list[0]); // 선택된 값
  const [showOptions, setShowOptions] = useState(false); // 옵션 표시 여부

  const handleOnChangeSelectValue = (e) => {
    setCurrentValue(e.target.getAttribute("value"));
  };

  useEffect(() => {
    // 드롭다운 박스 바깥쪽 클릭 시 옵션을 숨김
    function handleClickOutside(event) {
      if (selectRef.current && !selectRef.current.contains(event.target)) {
        setShowOptions(false);
      }
    }

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [selectRef]);

  return (
    <S.SelectBox onClick={() => setShowOptions((prev) => !prev)} ref={selectRef}>
      <S.Label>{currentValue}</S.Label>
      <S.SelectOptions show={showOptions}>
        {list.map((data, index) => (
          <S.Option key={index} value={data} onClick={handleOnChangeSelectValue}>
            {data}
          </S.Option>
        ))}
      </S.SelectOptions>
    </S.SelectBox>
  );
};

// props Default value
Dropdown.defaultProps = {
  name: "초기값",
};
