import React, { forwardRef, useEffect, useLayoutEffect, useRef } from 'react';
import Quill from 'quill';

const Editor = forwardRef(
  ({ readOnly, defaultValue, onTextChange, onSelectionChange }, ref) => {
    const containerRef = useRef(null);
    const defaultValueRef = useRef(defaultValue);
    const onTextChangeRef = useRef(onTextChange);
    const onSelectionChangeRef = useRef(onSelectionChange);

    useLayoutEffect(() => {
      onTextChangeRef.current = onTextChange;
      onSelectionChangeRef.current = onSelectionChange;
    });

    useEffect(() => {
      ref.current?.enable(!readOnly);
    }, [ref, readOnly]);

    useEffect(() => {
        const container = containerRef.current;
        const editorContainer = container.appendChild(
          container.ownerDocument.createElement('div'),
        );
        
        const options = {
            placeholder: 'Hello, World!',
            theme: 'snow'
          };

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
      
        ref.current = quill;
      
        if (defaultValueRef.current) {
          quill.setContents(defaultValueRef.current);
        }
      
        quill.on('text-change', (...args) => {
          onTextChangeRef.current?.(...args);
        });
      
        quill.on('selection-change', (...args) => {
          onSelectionChangeRef.current?.(...args);
        });
      
        return () => {
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