import * as S from "./notificationStyle"; 


const NotificationDetail = () => {
    return (
        <S.Detail>
            <S.NotificationBodyTop>
                        <S.BodyTitle>신청서 알림</S.BodyTitle>
                        <S.Date>2일전</S.Date>
            </S.NotificationBodyTop>
            <S.NotificationBodyContent>작성한 게시글에 참여 신청이 도착했어요.</S.NotificationBodyContent>
        </S.Detail>
    )
}
export default NotificationDetail;
