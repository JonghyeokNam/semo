package com.semoi.semo.chat.entity;

import com.semoi.semo.chat.dto.MessageDto;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

// 남종혁
@Document(collection = "messages")
@Data
public class Message {

    @Id
    private String id;

    private MessageDto.MessageType type;
    private String roomId;
    private String sender;
    private String content;
    private LocalDateTime time;
}
