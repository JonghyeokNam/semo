//package com.semoi.semo.chat.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.semoi.semo.chat.dto.ChatRoomDto;
//import com.semoi.semo.chat.entity.ChatRoom;
//import com.semoi.semo.chat.service.ChatService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.api.parallel.Execution;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@WebMvcTest(controllers = ChatRoomController.class)
//class ChatRoomControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private ChatService chatService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    @DisplayName("GET /chatroom/ - 채팅방 목록 조회 성공")
//    void chatRoomList_success() throws Exception {
//        // Given
//        ChatRoomDto chatRoomDto = new ChatRoomDto("room1", "Test Room", 2, Collections.emptyList());
//        List<ChatRoomDto> chatRoomDtos = Collections.singletonList(chatRoomDto);
//        Mockito.when(chatService.chatRoomList()).thenReturn(chatRoomDtos);
//
//        // When & Then
//        mockMvc.perform(get("/chatroom/")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("GET_CHATROOMLIST_SUCCESS"))
//                .andExpect(jsonPath("$.data[0].roomId").value("room1"))
//                .andExpect(jsonPath("$.data[0].roomName").value("Test Room"));
//    }
//
//}