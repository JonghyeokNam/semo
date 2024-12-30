package com.semoi.semo.chat.service;

import com.semoi.semo.chat.dto.ChatRoomDto;
import com.semoi.semo.chat.dto.MessageDto;

import java.util.List;

public interface ChatService {

    List<ChatRoomDto> chatRoomList();

    ChatRoomDto createRoom(String roomName);

    List<String> userList(String roomId);

    void enterRoom(String roomId, String userName);

    void leaveRoom(String roomId);

    List<MessageDto> getChatList(String roomId);
}



