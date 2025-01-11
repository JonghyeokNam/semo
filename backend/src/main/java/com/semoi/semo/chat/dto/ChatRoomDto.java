package com.semoi.semo.chat.dto;

import com.semoi.semo.chat.entity.ChatPart;
import com.semoi.semo.chat.entity.ChatRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomDto {

    private String roomId;
    private String roomName;
    private long userCount;

    private List<String> userList = new ArrayList<>();

    private String lastMessage;
    private LocalDateTime lastMessageTime;
    private long unreadCount;

    public ChatRoomDto(ChatRoom chatRoom, List<ChatPart> chatParts) {
        this.roomId = chatRoom.getRoomId();
        this.roomName = chatRoom.getRoomName();
        this.userCount = chatRoom.getUserCount();

        // ChatPart를 순회하면서, 각 사용자(User)의 username만 추출
        this.userList = chatParts.stream()
                .map(cp -> cp.getUser().getUsername()) // cp.getUser()가 null이 아닐 것이라 가정
                .collect(Collectors.toList());
    }
}
