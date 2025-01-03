package com.semoi.semo.chat.service;

import com.semoi.semo.chat.dto.ChatRoomDto;
import com.semoi.semo.chat.dto.MessageDto;

import java.util.List;

public interface ChatService {

    List<ChatRoomDto> chatRoomList(String loginEmail);

    ChatRoomDto createRoom(String roomName, String loginEmail);

    List<String> userList(String roomId);

    void enterRoom(String roomId, String loginEmail);

    void leaveRoom(String roomId, String loginEmail);

    List<MessageDto> getChatList(String roomId, String loginEmail);

    void readRoom(String roomId, String loginEmail);
}



