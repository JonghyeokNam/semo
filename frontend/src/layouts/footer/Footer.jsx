import React from "react";
import * as S from "./style";
import useMediaQueries from "../../hooks/useMediaQueries";

// 이유진
const Footer = () => {
    const {isDesktop } = useMediaQueries();

    return (
        <S.FooterWrapper $isDesktop={isDesktop}>
        
            <S.SeMOText>SeMO</S.SeMOText>
            <S.ContactText>Contact: semo.official@gmail.com</S.ContactText>
            <S.CopyrightText>Copyright SeMO. All rights reserved</S.CopyrightText>
            
            {/* 오른쪽에 위치한 항목들 */}
            <S.LinksContainer $isDesktop={isDesktop}>
                <S.LinkItem>이용약관</S.LinkItem>
                <S.LinkItem>개인정보처리방침</S.LinkItem>
                <S.LinkItem>서비스소개</S.LinkItem>
            </S.LinksContainer>
        </S.FooterWrapper>
    );
};

export default Footer;
