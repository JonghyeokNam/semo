package com.semoi.semo.chat.entity;

import com.semoi.semo.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "chat_parts")
public class ChatPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    private LocalDateTime enterTime;

    // 새로 추가 (이 유저가 이 방의 메시지를 마지막으로 확인한 시각)
    private LocalDateTime lastReadTime;

    public ChatPart(ChatRoom chatRoom, User user, LocalDateTime time){
        this.chatRoom = chatRoom;
        this.user = user;
        this.enterTime = time;
    }
}
