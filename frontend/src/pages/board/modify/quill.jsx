import React, { forwardRef, useEffect, useLayoutEffect, useRef } from 'react';
import Quill from 'quill';

const Editor = forwardRef(
  ({ readOnly, defaultValue, onTextChange, onSelectionChange }, ref) => {
    const containerRef = useRef(null);
    const defaultValueRef = useRef(defaultValue);
    const onTextChangeRef = useRef(onTextChange);
    const onSelectionChangeRef = useRef(onSelectionChange);

    useLayoutEffect(() => {
      // 매 렌더마다 최신 onTextChange, onSelectionChange를 업데이트
      onTextChangeRef.current = onTextChange;
      onSelectionChangeRef.current = onSelectionChange;
    });

    useEffect(() => {
      // quill 인스턴스가 이미 있으면 readOnly 설정
      ref.current?.enable(!readOnly);
    }, [ref, readOnly]);

    useEffect(() => {
        const container = containerRef.current;
        // 에디터를 표시할 DOM 요소 생성
        const editorContainer = container.appendChild(
          container.ownerDocument.createElement('div'),
        );
        
        const options = {
            placeholder: 'Hello, World!',
            theme: 'snow'
          };

        // Quill 인스턴스 생성 
        const quill = new Quill(editorContainer, {
          theme: 'snow',
          modules: {
            toolbar: [
              ['bold', 'italic', 'underline', 'strike'], // 텍스트 스타일
              [{ header: [1, 2, 3, false] }], // 헤더 크기
              [{ color: [] }, { background: [] }], // 색상 옵션
              [{ list: 'ordered' }], // 리스트
              ['link'], // 링크 추가
              ['clean'], // 포맷 초기화
            ],
          },
        }, options);
      

        // 부모에서 forwardRef로 접근할 수 있도록 ref.current에 할당
        ref.current = quill;
      
        // defaultValue가 있으면 Quill에 적용
        if (defaultValueRef.current) {
          quill.setContents(defaultValueRef.current);
        }
      
        // text-change 이벤트: 4번째 인자로 quill을 넘겨준다
        quill.on('text-change', (delta, oldDelta, source) => {
          onTextChangeRef.current?.(delta, oldDelta, source, quill);
        });
      
        quill.on('selection-change', (...args) => {
          onSelectionChangeRef.current?.(...args);
        });
      
        return () => {
          // 언마운트 시 정리
          ref.current = null;
          container.innerHTML = '';
        };
      }, [ref]);
      

    return (
      <div
        ref={containerRef}
        style={{
          borderRadius: '10px',
          
        }}
      ></div>
    );
  },
);

Editor.displayName = 'Editor';

export default Editor;