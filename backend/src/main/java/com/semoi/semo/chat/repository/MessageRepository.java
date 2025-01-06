package com.semoi.semo.chat.repository;

import com.semoi.semo.chat.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

// 남종혁
public interface MessageRepository extends MongoRepository<Message, String> {

    public List<Message> findAllByRoomIdAndTimeAfter(String roomId, LocalDateTime time);

    // 가장 최근(시간 내림차순) 한 개만 가져오기
    Message findTopByRoomIdOrderByTimeDesc(String roomId);

    long countByRoomIdAndTimeAfter(String roomId, LocalDateTime time);
}
