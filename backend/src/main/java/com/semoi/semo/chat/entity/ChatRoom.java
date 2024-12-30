package com.semoi.semo.chat.entity;

import com.semoi.semo.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
