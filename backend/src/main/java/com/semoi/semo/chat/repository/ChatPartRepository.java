package com.semoi.semo.chat.repository;

import com.semoi.semo.chat.entity.ChatPart;
import com.semoi.semo.chat.entity.ChatRoom;
import com.semoi.semo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatPartRepository extends JpaRepository<ChatPart, User> {

    // 특정 ChatRoom, 특정 User가 이미 참여 중인지 확인
    boolean existsByChatRoomAndUser(ChatRoom chatRoom, User user);

    // 특정 ChatRoom, 특정 User의 참여 정보 조회
    Optional<ChatPart> findByChatRoomAndUser(ChatRoom chatRoom, User user);

    // 해당 채팅방에 참여 중인 전체 ChatPart 목록 조회
    List<ChatPart> findByChatRoom(ChatRoom chatRoom);
}
