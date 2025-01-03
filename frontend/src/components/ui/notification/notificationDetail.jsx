import * as S from "./notificationStyle"; 
import { formatDistanceToNow } from "date-fns";
import { ko } from "date-fns/locale";
import { useReadNotificationStore, useCheckNoReadNotificationStore, useGetNotificationsStore } from "../../../store/useNotificationStore";
import { useNavigate } from "react-router-dom";

const NotificationDetail = ({ item, onClose }) => {
    const navigate = useNavigate();

    // createdAt을 상대 시간으로 변환
    const relativeTime = formatDistanceToNow(new Date(item.createdAt), { addSuffix: true, locale: ko });
    const { readNotification } = useReadNotificationStore();
    const { fechIsReadAll } = useCheckNoReadNotificationStore();
    const { fetchList } = useGetNotificationsStore();
    
    // 클릭 이벤트 핸들러
    const handleClick = async () => {
        await readNotification(item.notificationId); // 알림 읽음 처리 API 호출
        fechIsReadAll();
        navigate("/board/detail", {
            state: { boardId: item.boardId }, // boardId를 state로 전달
        });
        onClose();
        fetchList();
    };

    return (
        <S.Detail isRead={item.isRead} onClick={handleClick}>
            <S.NotificationBodyTop>
                        <S.BodyTitle>{item.title}</S.BodyTitle>
                        <S.Date>{relativeTime}</S.Date>
            </S.NotificationBodyTop>
            <S.NotificationBodyContent>{item.content}</S.NotificationBodyContent>
        </S.Detail>
    )
}
export default NotificationDetail;
