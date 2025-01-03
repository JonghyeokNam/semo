import { IoClose } from "react-icons/io5";
import * as S from "./notificationStyle"; 
import NotificationDetail from "./notificationDetail"
import { useGetNotificationsStore } from "../../../store/useNotificationStore";
import { useEffect } from "react";


const Notification = ({ isOpen, onClose }) => {
    // isOpen이 false일 경우 모달을 렌더링하지 않음
    
    const { list, fetchList } = useGetNotificationsStore();
    
    useEffect(() => {
        fetchList(); // 데이터 가져오기
    }, [fetchList]);
    
    if (!isOpen) return null;

    return (
        <S.Overlay onClick={onClose}>
            <S.NotificationContent onClick={(e) => e.stopPropagation()}>
                <S.NotificationHead>
                    <S.Title>알림</S.Title>
                    <S.Close onClick={onClose}>
                        <IoClose/>
                    </S.Close>
                </S.NotificationHead>

                <S.NotificationBody>
                    {list.map((item, index) => {
                        return !item.isRead ? (
                            <NotificationDetail key={index} item={item} onClose={onClose} />
                        ) : null;
                    })}
                </S.NotificationBody>

            </S.NotificationContent>
        </S.Overlay>
    );
};
    
export default Notification;
    