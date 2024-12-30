package com.semoi.semo.chat.entity;

import com.semoi.semo.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ChatPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ChatRoom chatRoom;

//    @JoinColumn(name = "MEMBER_ID", insertable = false, updatable = false)
    @ManyToOne
    private User user;

    private LocalDateTime enterTime;

    public ChatPart(ChatRoom chatRoom, User user, LocalDateTime time){
        this.chatRoom = chatRoom;
        this.user = user;
        this.enterTime = time;
    }
}
