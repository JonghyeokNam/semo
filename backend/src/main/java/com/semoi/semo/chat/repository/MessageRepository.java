package com.semoi.semo.chat.repository;

import com.semoi.semo.chat.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    public List<Message> findAllByRoomIdAndTimeAfter(String roomId, LocalDateTime time);
}
