package com.semoi.semo.chat.controller;

import com.semoi.semo.chat.dto.MessageDto;
import com.semoi.semo.chat.entity.Message;
import com.semoi.semo.chat.repository.MessageRepository;
import com.semoi.semo.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/ws")
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final ModelMapper modelMapper;

    // /pub/chat.enter.{chatRoomId} 로 STOMP 메시지가 들어오면 처리
    // (클라이언트에서: stompClient.send("/pub/chat.enter.123", {...}) 형태)
    @MessageMapping("chat.enter.{chatRoomId}")
    public void enterUser(@Payload MessageDto chat, @DestinationVariable String chatRoomId) {

        // 1. 채팅방에 유저 추가
        chatService.enterRoom(chat.getRoomId(), chat.getSender());

        // 2. 메시지 내용 설정 (입장 알림)
        chat.setTime(LocalDateTime.now());
        chat.setContent(chat.getSender() + " 님이 입장하셨습니다!");
        log.info("[ENTER] roomId={}, sender={}", chat.getRoomId(), chat.getSender());

        // 3. MongoDB에 저장
        Message message = modelMapper.map(chat, Message.class);
        message.setId(null);
        messageRepository.save(message);

        // 4. STOMP 브로커로 전송 -> 해당 채팅방을 구독 중인 클라이언트에게 메시지 전달
        // 클라이언트는 "/topic/chat/room.{chatRoomId}"를 subscribe()하고 있어야 메시지 받음
        messagingTemplate.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

    // /pub/chat.message.{chatRoomId} 로 STOMP 메시지가 들어오면 처리
    @MessageMapping("chat.message.{chatRoomId}")
    public void sendMessage(@Payload MessageDto chat, @DestinationVariable String chatRoomId) {
        // 1. 시간 설정
        chat.setTime(LocalDateTime.now());
        log.info("[MESSAGE] roomId={}, sender={}, content={}",
                chat.getRoomId(), chat.getSender(), chat.getContent());

        // 2. MongoDB에 저장
        Message message = modelMapper.map(chat, Message.class);
        message.setId(null);
        messageRepository.save(message);

        // 3. STOMP 브로커를 통해 채팅방 구독자들에게 메시지 전송
        messagingTemplate.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }
}
