package com.semoi.semo.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 남종혁
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    public enum MessageType {
        ENTER, TALK, LEAVE;
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String content;

    private LocalDateTime time;
}
