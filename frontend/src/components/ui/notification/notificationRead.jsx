import { IoClose } from "react-icons/io5";
import * as S from "./notificationStyle"; 
import NotificationDetail from "./notificationDetail"
import { useGetNotificationsStore } from "../../../store/notificationStore";
import { useEffect } from "react";


const Notification = ({ isOpen, onClose }) => {
    // isOpen이 false일 경우 모달을 렌더링하지 않음
    
    // const { list, fetchList, loading, error } = useGetNotificationsStore();
    
    // useEffect(() => {
    //     fetchList(); // 데이터 가져오기
    // }, [fetchList]);
    
    if (!isOpen) return null;

    // if (loading) return <p>Loading...</p>;
    // if (error) return <p>Error: {error}</p>;

    // console.log(list);

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
                    <NotificationDetail/>
                </S.NotificationBody>

            </S.NotificationContent>
        </S.Overlay>
    );
};
    
export default Notification;
    