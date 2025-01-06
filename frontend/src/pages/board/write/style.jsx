        import styled from "styled-components";

        // 이유진
        export const Container = styled.div`
        width: 100%;
        margin: 0 auto;
        `;

        export const Grid = styled.div`
        display: grid;
        grid-template-columns: ${(props) => 
        props.$isDesktop 
            ? "1fr 1fr 1fr" 
            : props.$isTablet 
            ? "1fr 1fr" 
            : "1fr"};
        `;

        export const title = styled.h2`
        font-size: 30px;
        font-weight: bold;
        `;

        export const FormSection = styled.div`
        margin: 30px 0;
        `;

        export const Separator = styled.hr`
        border: none;
        border-top: 1px solid #F2F2F2;
        margin: 20px 0;
        `;

        export const Row = styled.div`
        display: flex;
        justify-content: space-between;
        margin-bottom: 20px;

        flex-wrap: wrap; 
        `;

        export const Column = styled.div`
        &:last-child {
            margin-right: 0;
        }
        `;

        export const Label = styled.label`
        display: block;
        font-size: 16px;
        font-weight: bold;
        margin-bottom: 8px;
        `;

    
        export const TextInput = styled.input`
        width: 100%;
        padding: 10px;
        font-size: 14px;
        border: 1px solid #D9D9D9;
        border-radius: 10px;
        box-sizing: border-box;
        margin-bottom: 10px;

        &:focus {
        border-color: var(--green); 
        outline: none;
    }
        `;

        export const EditorContainer = styled.div`

        position: relative;
    margin-bottom: 20px;


        .ql-editor {
        min-height: 200px; /* 최소 높이 설정 */
        padding: 10px; /* 추가 스타일 */
        box-sizing: border-box; /* 크기 조정 포함 */
    }

        .ql-editor p strong {
        font-weight: bold;
        }

        .ql-editor p em {
        font-style: italic;
        }

        `;


    export const ButtonContainer = styled.div`
    display: flex;
    justify-content: flex-end;
    gap: 10px; 

    `;

    export const CancelButton = styled.button`
    padding: 10px 20px;
    font-size: 14px;
    border: 1px solid #d9d9d9;
    border-radius: 5px;
    background-color: #ffffff;
    color: #777777;
    cursor: pointer;
        font-weight: bold;

    &:hover {
        background-color: #f2f2f2;
    }
    `;

    export const SubmitButton = styled.button`
    padding: 10px 20px;
    font-size: 14px;
    border: none;
    font-weight: bold;
    border-radius: 5px;
    background-color: var(--green);
    color: white;
    cursor: pointer;

    &:hover {
        background-color: #3D8D4A;
    }
    `;
