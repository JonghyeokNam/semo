package com.semoi.semo.chat.controller;

import com.semoi.semo.chat.dto.ChatRoomDto;
import com.semoi.semo.chat.dto.MessageDto;
import com.semoi.semo.chat.service.ChatService;
import com.semoi.semo.global.response.Response;
import com.semoi.semo.jwt.service.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatrooms")
public class ChatRoomController {

    private final ChatService chatService;
    private final TokenProvider tokenProvider;

    // 채팅방 목록 조회
    @GetMapping("/")
    public ResponseEntity<Response> chatRoomList(HttpServletRequest request) {
        String loginEmail = tokenProvider.getUserLoginEmail(request);
        List<ChatRoomDto> chatRoomDtos = chatService.chatRoomList(loginEmail);
        return ResponseEntity.ok(Response.of("GET_CHATROOMLIST_SUCCESS", chatRoomDtos));
    }

    // 채팅방 생성
    @PostMapping("/")
    public ResponseEntity<Response> createRoom(HttpServletRequest request, @RequestParam String name) {
        String loginEmail = tokenProvider.getUserLoginEmail(request);
        ChatRoomDto room = chatService.createRoom(name, loginEmail);
        return ResponseEntity.ok(Response.of("CREATE_CHATROOM_SUCCESS", room.getRoomId()));
    }

    // 채팅방 나가기
    @PostMapping("/leave")
    public ResponseEntity<Response> leaveRoom(HttpServletRequest request, @RequestParam String roomId) {
        String loginEmail = tokenProvider.getUserLoginEmail(request);
        chatService.leaveRoom(roomId, loginEmail);
        return ResponseEntity.ok(Response.of("LEAVE_ROOM_SUCCESS", roomId));
    }

    // 채팅 읽음 처리
    @PostMapping("/read")
    public ResponseEntity<Response> readRoom(HttpServletRequest request,
                                             @RequestParam String roomId) {
        String loginEmail = tokenProvider.getUserLoginEmail(request);
        chatService.readRoom(roomId, loginEmail);
        return ResponseEntity.ok(Response.of("READ_ROOM_SUCCESS", roomId));
    }

    // 채팅방 채팅내용 불러오기 (방 열기)
    @GetMapping("/chats")
    public ResponseEntity<Response> getChatList(HttpServletRequest request, String roomId) {
        String loginEmail = tokenProvider.getUserLoginEmail(request);
        List<MessageDto> chats = chatService.getChatList(roomId, loginEmail);
        return ResponseEntity.ok(Response.of("GET_CHATLIST_SUCCESS", chats));
    }

    // 채팅에 참여한 유저 리스트 반환
    @GetMapping("/userlists")
    public ResponseEntity<Response> userList(String roomId) {
        List<String> userList = chatService.userList(roomId);
        return ResponseEntity.ok(Response.of("GET_CHAT_USERLIST_SUCCESS", userList));
    }
}
