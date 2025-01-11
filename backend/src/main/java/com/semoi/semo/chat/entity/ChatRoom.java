package com.semoi.semo.chat.entity;

import jakarta.persistence.*;
import lombok.*;

// 남종혁
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_rooms")
public class ChatRoom {

    @Id
    private String roomId;
    private String roomName;
    private long userCount;

    public void upUserCount() {
        this.userCount++;
    }

    public void downUserCount() {
        this.userCount--;
    }
}
