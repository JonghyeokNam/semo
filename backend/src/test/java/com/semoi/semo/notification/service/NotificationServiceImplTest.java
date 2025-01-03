//package com.semoi.semo.notification.service;
//
//import com.semoi.semo.campus.domain.Campus;
//import com.semoi.semo.campus.enums.CampusName;
//import com.semoi.semo.board.entity.Board;
//import com.semoi.semo.notification.dto.NotificationResponseDto;
//import com.semoi.semo.notification.entity.Notification;
//import com.semoi.semo.notification.enums.Type;
//import com.semoi.semo.notification.repository.NotificationRepository;
//import com.semoi.semo.user.domain.User;
//import com.semoi.semo.position.entity.Position;
//import com.semoi.semo.user.enums.Role;
//import com.semoi.semo.user.service.UserService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//@DisplayName("알림 서비스 단위 테스트")
//class NotificationServiceImplTest {
//
//    @Mock
//    NotificationRepository notificationRepository;
//    @Mock
//    UserService userService;
//    @InjectMocks
//    NotificationServiceImpl notificationService;
//
//    @Test
//    @DisplayName("읽지 않은 알람 유무 테스트")
//    void checkNotification() throws Exception {
//        User user = setUser();
//
//        when(notificationRepository.existsByUserAndIsReadFalse(user)).thenReturn(true);
//        when(userService.getUserByLoginEmailOrElseThrow(user.getLoginEmail())).thenReturn(user);
//
//        // when
//        boolean b = notificationService.checkNotification(user.getLoginEmail());
//
//        // then
//        assertThat(b).isTrue();
//    }
//
//    @Test
//    @DisplayName("사용자의 모든 알림 조회")
//    void getNotifications() throws Exception {
//        User user = setUser();
//
//        Notification notification = Notification.create(
//                Type.RECRUITMENT_PERIOD_END,
//                user,
//                new Board()
//        );
//
//        when(userService.getUserByLoginEmailOrElseThrow(user.getLoginEmail())).thenReturn(user);
//        when(notificationRepository.findAllByUserOrderByCreatedAtDesc(user)).thenReturn(List.of(notification));
//
//        List<NotificationResponseDto> notifications = notificationService.getNotifications(user.getLoginEmail());
//
//        assertThat(notifications.size()).isEqualTo(1);
//        assertThat(notifications.getFirst().isRead()).isFalse();
//    }
//
//    private User setUser() throws Exception {
//        // given
//        String userEmail = "test@test.com";
//        return User.create("test", userEmail, userEmail, Position.UNDECIDED, Role.USER);
//    }
//
//    private Campus setCampusByReflection() throws Exception {
//        Constructor<Campus> constructor = Campus.class.getDeclaredConstructor();
//        constructor.setAccessible(true); // protected 생성자 접근 가능
//        Campus campus = constructor.newInstance();
//
//        Field idField = Campus.class.getDeclaredField("campusId");
//        idField.setAccessible(true);
//        idField.set(campus, 1L);
//
//        Field nameField = Campus.class.getDeclaredField("campusName");
//        nameField.setAccessible(true);
//        nameField.set(campus, CampusName.DONGDAEMUN);
//
//        return campus;
//    }
//
//
//
//}