package com.semoi.semo.chat.controller;

import com.semoi.semo.board.entity.Board;
import com.semoi.semo.board.repository.BoardRepository;
import com.semoi.semo.chat.dto.ChatRoomDto;
import com.semoi.semo.chat.dto.MessageDto;
import com.semoi.semo.chat.service.ChatService;
import com.semoi.semo.global.response.Response;
import com.semoi.semo.jwt.service.TokenProvider;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/chatrooms")
public class ChatRoomController {

    private final ChatService chatService;
    private final TokenProvider tokenProvider;
    private final BoardRepository boardRepository;

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

    @PostMapping("/createByPost")
    public ResponseEntity<Response> createRoomByPost(HttpServletRequest request, @RequestParam Long boardId) {
        // 1) 현재 로그인 중인 사용자
        String loginEmail = tokenProvider.getUserLoginEmail(request);

        // 2) boardId로 게시글 조회 -> 작성자 User를 찾음
        String authorEmail = chatService.getAuthorLoginEmail(boardId);

        // 3) chatService.createRoomByEmails( loginEmail, authorEmail ) 호출
        //    -> "두 명 모두 방에 참여"하도록 처리
        //    -> 새 방의 roomId 반환
        ChatRoomDto roomDto = chatService.createRoomByEmails(loginEmail, authorEmail);

        // 4) 응답으로 roomId
        return ResponseEntity.ok(Response.of("CREATE_CHATROOM_SUCCESS", roomDto.getRoomId()));
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
