package com.semoi.semo.chat.repository;

import com.semoi.semo.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

// 남종혁
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
}
