package com.semoi.semo.notification.repository;

import com.semoi.semo.notification.entity.Notification;
import com.semoi.semo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    boolean existsByUserAndIsReadFalse(User user);

    List<Notification> findAllByUserOrderByCreatedAtDesc(User user);
}
