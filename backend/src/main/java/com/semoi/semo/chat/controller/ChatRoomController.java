package com.semoi.semo.chat.controller;

import com.semoi.semo.chat.dto.ChatRoomDto;
import com.semoi.semo.chat.dto.MessageDto;
import com.semoi.semo.chat.service.ChatService;
import com.semoi.semo.common.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatroom")
public class ChatRoomController {

    private final ChatService chatService;

    // 채팅방 목록 조회
    @GetMapping("/")
    public ResponseEntity<Response> chatRoomList() {
        List<ChatRoomDto> chatRoomDtos = chatService.chatRoomList();
        return ResponseEntity.ok(Response.of("GET_CHATROOMLIST_SUCCESS", chatRoomDtos));
    }

    // 채팅방 생성
    @PostMapping("/")
    public ResponseEntity<Response> createRoom(@RequestParam String name) {
        ChatRoomDto room = chatService.createRoom(name);
        return ResponseEntity.ok(Response.of("CREATE_CHATROOM_SUCCESS", room.getRoomId()));
    }

    // 채팅방 나가기
    @DeleteMapping("/")
    public ResponseEntity<Response> leaveRoom(@RequestParam String roomId) {
        chatService.leaveRoom(roomId);
        return ResponseEntity.ok(Response.of("LEAVE_ROOM_SUCCESS", roomId));
    }

    // 채팅방 채팅내용 불러오기 (방 열기)
    @GetMapping("/chats")
    public ResponseEntity<Response> getChatList(String roomId) {
        List<MessageDto> chats = chatService.getChatList(roomId);
        return ResponseEntity.ok(Response.of("GET_CHATLIST_SUCCESS", chats));
    }

    // 채팅에 참여한 유저 리스트 반환
    @GetMapping("/userlist")
    public ResponseEntity<Response> userList(String roomId) {
        List<String> userList = chatService.userList(roomId);
        return ResponseEntity.ok(Response.of("GET_CHAT_USERLIST_SUCCESS", userList));
    }
}
