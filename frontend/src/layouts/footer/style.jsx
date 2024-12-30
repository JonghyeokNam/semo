    import styled from "styled-components";

    // Footer 스타일링
    export const FooterWrapper = styled.div`
    display: flex;
    flex-direction: column;
    align-items: flex-start;  /* 왼쪽 정렬 */
    justify-content: center;
    width: 100%;
    height: 180px;
    padding: ${(props) => (props.$isDesktop ? "0 250px" : "0 48px")};
    background-color: #f9f9f9;
    text-align: left;  /* 텍스트 왼쪽 정렬 */
    `;


    export const SeMOText = styled.h1`
    font-size: 30px;
    color: var(--green);
    margin-bottom: 20px;  /* 아래쪽 간격 */
    font-weight: 600;
    `;

    export const ContactText = styled.p`
    font-size: 13px;
    color: #777777;
    margin-bottom: 10px;  /* 아래쪽 간격 */
    `;

    export const CopyrightText = styled.p`
    font-size: 13px;
    color: #777777;
    `;

    // 오른쪽 링크 컨테이너
    export const LinksContainer = styled.div`
    display: flex;
    justify-content: flex-end;  /* 오른쪽 정렬 */
    gap: 50px;  /* 항목 간 간격 */
    width: 100%;
    margin-top: 10px;
    font-size: ${(props) => (props.$isDesktop ? "14px" : "10px")};


    `;

    // 개별 링크 항목 스타일
    export const LinkItem = styled.span`
    color: #2f2f2f;
    cursor: pointer;
    text-decoration: none;

    &:hover {
        text-decoration: underline;
    }
    `;
