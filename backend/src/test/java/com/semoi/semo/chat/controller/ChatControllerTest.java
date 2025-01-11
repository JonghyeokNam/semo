package com.semoi.semo.chat.controller;

import com.semoi.semo.chat.dto.MessageDto;
import com.semoi.semo.chat.entity.Message;
import com.semoi.semo.chat.repository.MessageRepository;
import com.semoi.semo.chat.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatControllerTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ChatService chatService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ChatController chatController;

    private MessageDto sampleMessageDto;

    @BeforeEach
    void setUp() {
        sampleMessageDto = new MessageDto();
        sampleMessageDto.setRoomId("TestRoomIdA");
        sampleMessageDto.setSender("TestUserA");
        sampleMessageDto.setContent("TestContentA");
    }

    @Test
    void testEnterUser() {
        // Given
        String chatRoomId = "TestRoomIdA";
        MessageDto chatDto = new MessageDto();
        chatDto.setRoomId(chatRoomId);
        chatDto.setSender("TestUserA");
        chatDto.setContent("UserA 님이 입장하셨습니다!");
        chatDto.setTime(LocalDateTime.now());

        Message messageEntity = new Message();
        when(modelMapper.map(any(MessageDto.class), eq(Message.class))).thenReturn(messageEntity);

        // When
        chatController.enterUser(chatDto, chatRoomId);

        // Then
        // 1. 채팅방에 유저 추가
        verify(chatService, times(1)).enterRoom(chatRoomId, "TestUserA");

        // 2. 메시지 설정 및 저장
        assertNull(messageEntity.getId());
        verify(messageRepository, times(1)).save(messageEntity);
        verify(modelMapper, times(1)).map(chatDto, Message.class);

        // 3. 메시지 전송
        verify(messagingTemplate, times(1)).convertAndSend("/sub/chat/room/" + chatRoomId, chatDto);
    }

    @Test
    void testSendMessage() {
        // Given
        String chatRoomId = "123";
        MessageDto chatDto = new MessageDto();
        chatDto.setRoomId(chatRoomId);
        chatDto.setSender("UserA");
        chatDto.setContent("Hello!");
        chatDto.setTime(LocalDateTime.now());

        Message messageEntity = new Message();
        when(modelMapper.map(any(MessageDto.class), eq(Message.class))).thenReturn(messageEntity);

        // When
        chatController.sendMessage(chatDto, chatRoomId);

        // Then
        // 1. 시간 설정 및 로그는 검증 대상에서 제외 (로그는 실제 테스트에서 확인하기 어려움)

        // 2. 메시지 저장
        assertNull(messageEntity.getId());
        verify(messageRepository, times(1)).save(messageEntity);
        verify(modelMapper, times(1)).map(chatDto, Message.class);

        // 3. 메시지 전송
        verify(messagingTemplate, times(1)).convertAndSend("/sub/chat/room/" + chatRoomId, chatDto);
    }
}