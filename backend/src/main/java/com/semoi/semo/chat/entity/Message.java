package com.semoi.semo.chat.entity;

import com.semoi.semo.chat.dto.MessageDto;
import com.semoi.semo.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "message")
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
