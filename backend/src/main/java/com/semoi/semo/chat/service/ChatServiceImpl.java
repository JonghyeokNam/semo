package com.semoi.semo.chat.service;


import com.semoi.semo.board.entity.Board;
import com.semoi.semo.board.repository.BoardRepository;
import com.semoi.semo.chat.dto.ChatRoomDto;
import com.semoi.semo.chat.dto.MessageDto;
import com.semoi.semo.chat.entity.ChatPart;
import com.semoi.semo.chat.entity.ChatRoom;
import com.semoi.semo.chat.entity.Message;
import com.semoi.semo.chat.repository.ChatPartRepository;
import com.semoi.semo.chat.repository.ChatRoomRepository;
import com.semoi.semo.chat.repository.MessageRepository;
import com.semoi.semo.user.domain.User;
import com.semoi.semo.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// 남종혁
@Log4j2
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatPartRepository chatPartRepository;
    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    @Override
    public List<ChatRoomDto> chatRoomList(String loginEmail) {
        // 1) 이메일로 User 엔티티 조회
        User user = userRepository.findByLoginEmail(loginEmail)
                .orElseThrow(() -> new EntityNotFoundException("USER_NOT_FOUND"));

        // 2) ChatPart에서 해당 User가 속한 목록 찾기
        List<ChatPart> userChatParts = chatPartRepository.findByUser(user);

        // 3) userChatParts에 있는 chatRoom들만 추출, 중복 제거
        List<ChatRoom> userRooms = userChatParts.stream()
                .map(ChatPart::getChatRoom)
                .distinct()
                .collect(Collectors.toList());

        // 4) 각 방에 대한 DTO 생성 + 마지막 메시지/안 읽은 메시지 설정
        return userRooms.stream()
                .map(chatRoom -> {
                    // (a) Dto 기본 생성
                    List<ChatPart> chatParts = chatPartRepository.findByChatRoom(chatRoom);
                    ChatRoomDto dto = new ChatRoomDto(chatRoom, chatParts);

                    // (b) 가장 최근 메시지 (MongoDB)
                    Message lastMsg = messageRepository.findTopByRoomIdOrderByTimeDesc(chatRoom.getRoomId());
                    if (lastMsg != null) {
                        dto.setLastMessage(lastMsg.getContent());
                        dto.setLastMessageTime(lastMsg.getTime());
                    } else {
                        dto.setLastMessage("");
                        dto.setLastMessageTime(null);
                    }

                    // (c) 안 읽은 메시지 개수 계산
                    //     현재 유저의 ChatPart를 찾고 -> lastReadTime 기준으로 count
                    ChatPart cp = chatPartRepository.findByChatRoomAndUser(chatRoom, user)
                            .orElse(null);

                    if (cp != null && cp.getLastReadTime() != null) {
                        long unread = messageRepository.countByRoomIdAndTimeAfter(
                                chatRoom.getRoomId(),
                                cp.getLastReadTime()
                        );
                        dto.setUnreadCount(unread);
                    } else {
                        // 참여 중이긴 하나 lastReadTime이 없거나,
                        // 참여 기록이 없는 경우(이론상 없지만)
                        // => 전부 안 읽은 것으로 볼 수도 있고, 임시로 0 처리
                        dto.setUnreadCount(0);
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void readRoom(String roomId, String loginEmail) {
        User user = userRepository.findByLoginEmail(loginEmail)
                .orElseThrow(() -> new EntityNotFoundException("USER_NOT_FOUND"));
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("CHATROOM_NOT_FOUND"));
        ChatPart chatPart = chatPartRepository
                .findByChatRoomAndUser(chatRoom, user)
                .orElseThrow(() -> new EntityNotFoundException("CHATROOM_MEMBER_NOT_FOUND"));

        // 읽음 처리
        chatPart.setLastReadTime(LocalDateTime.now());
        chatPartRepository.save(chatPart);
    }

    @Override
    @Transactional
    public ChatRoomDto createRoom(String roomName, String loginEmail) {
        User user = userRepository.findByLoginEmail(loginEmail)
                .orElseThrow(() -> new EntityNotFoundException("USER_NOT_FOUND"));
        // 1) 새 채팅방 엔티티 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .userCount(0)
                .build();

        // 2) 저장
        chatRoomRepository.save(chatRoom);

        // 3) 방 생성 후, 해당 유저가 바로 입장한다고 가정
        chatRoom.upUserCount();
        ChatPart chatPart = new ChatPart(chatRoom, user, LocalDateTime.now());
        chatPartRepository.save(chatPart);

        // 3) 방을 처음 만들 때는 참여자가 없으므로 빈 리스트
        return new ChatRoomDto(chatRoom, new ArrayList<>());
    }

    @Override
    @Transactional
    public ChatRoomDto createRoomByEmails(String loginEmail, String authorEmail) {
        // 0) 같은 이메일로 요청이 들어온 경우 처리 (원한다면)
        if (loginEmail.equals(authorEmail)) {
            throw new IllegalArgumentException("SAME_USER_FOR_1_1_CHAT");
        }

        // 1) 각각 User 엔티티 조회
        User loginUser = userRepository.findByLoginEmail(loginEmail)
                .orElseThrow(() -> new EntityNotFoundException("LOGIN_USER_NOT_FOUND"));
        User author = userRepository.findByLoginEmail(authorEmail)
                .orElseThrow(() -> new EntityNotFoundException("AUTHOR_NOT_FOUND"));

        // 2) user1이 참여하고 있는 모든 ChatPart 조회
        //    => user1이 속한 모든 ChatRoom 목록을 구한 뒤,
        //    => 각각의 방에 user2도 참여하고 있으면 → "이미 존재하는 1:1 방"
        List<ChatPart> loginUserParts = chatPartRepository.findByUser(loginUser);
        List<ChatRoom> loginUserRooms = loginUserParts.stream()
                .map(ChatPart::getChatRoom)
                .distinct()
                .collect(Collectors.toList());

        for (ChatRoom room : loginUserRooms) {
            // 그 방에 user2가 포함되어 있는지 확인
            boolean authorExists = chatPartRepository.existsByChatRoomAndUser(room, author);
            // 2명 다 참여중이라면 "이미 존재하는 1:1 방"으로 간주
            if (authorExists) {
                // DTO 생성 후 반환
                List<ChatPart> chatParts = chatPartRepository.findByChatRoom(room);
                return new ChatRoomDto(room, chatParts);
            }
        }

        // 3) 여기까지 왔다면 "user1과 user2가 함께 속해있는 방"이 없으므로 새 방 생성
        ChatRoom newRoom = ChatRoom.builder()
                .roomId(UUID.randomUUID().toString())
                // 방 이름은 자유롭게 지정 (ex: "user1-user2")
                .roomName(loginUser.getUsername() + " - " + author.getUsername())
                .userCount(0)
                .build();
        chatRoomRepository.save(newRoom);

        // (A) userCount를 2 올림 (2명이 참여)
        newRoom.upUserCount();
        newRoom.upUserCount();

        // (B) user1 참여
        ChatPart part1 = new ChatPart(newRoom, loginUser, LocalDateTime.now());
        chatPartRepository.save(part1);

        // (C) user2 참여
        ChatPart part2 = new ChatPart(newRoom, author, LocalDateTime.now());
        chatPartRepository.save(part2);

        // 4) 최종적으로 ChatRoomDto로 변환해서 반환
        List<ChatPart> newRoomParts = Arrays.asList(part1, part2);
        return new ChatRoomDto(newRoom, newRoomParts);
    }

    // 채팅방에 참여 중인 사용자 목록
    @Override
    public List<String> userList(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("CHATROOM_NOT_FOUND")); // 에러코드 추가 필요

        // 현재 채팅방에 참여 중인 참가자 조회
        List<ChatPart> chatParts = chatPartRepository.findByChatRoom(chatRoom);

        // 참여 중인 User들의 username 목록 추출
        List<String> users = chatParts.stream()
                .map(chatPart -> chatPart.getUser().getUsername())
                .collect(Collectors.toList());

        return users;
    }

    // 채팅방 입장 처리
    @Override
    @Transactional
    public void enterRoom(String roomId, String loginEmail) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new EntityNotFoundException("CHATROOM_NOT_FOUND"));
        User user = userRepository.findByLoginEmail(loginEmail).orElseThrow();

        // 채팅방 인원 수 증가
        chatRoom.upUserCount();

        // 이미 참여 중인지 체크
        if (chatPartRepository.existsByChatRoomAndUser(chatRoom, user)){
            throw new IllegalStateException("ALREADY_ENTER_ROOM");
        }

        // 새 ChatPart(= ChatRoomMember) 생성 & 저장
        ChatPart chatPart = new ChatPart(chatRoom, user, LocalDateTime.now());
        chatPartRepository.save(chatPart);
    }

    @Override
    @Transactional
    public void leaveRoom(String roomId, String loginEmail) {
        // 1) 토큰에서 꺼낸 userEmail로 User 조회
        User user = userRepository.findByLoginEmail(loginEmail)
                .orElseThrow(() -> new EntityNotFoundException("USER_NOT_FOUND"));

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new EntityNotFoundException("CHATROOM_NOT_FOUND"));
        chatRoom.downUserCount();

        // 채팅방-사용자 참여 정보 조회 후 삭제
        ChatPart chatPart = chatPartRepository.findByChatRoomAndUser(chatRoom, user).orElseThrow(()->new EntityNotFoundException("CHATROOM_MEMBER_NOT_FOUND"));
        chatPartRepository.delete(chatPart);
    }

    // 해당 채팅방 입장 시점 이후의 메시지 목록 가져오기
    @Override
    public List<MessageDto> getChatList(String roomId, String loginEmail) {
        // 1) 토큰에서 꺼낸 userEmail로 User 조회
        User user = userRepository.findByLoginEmail(loginEmail)
                .orElseThrow(() -> new EntityNotFoundException("USER_NOT_FOUND"));

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new EntityNotFoundException("CHATROOM_NOT_FOUND"));

        // 사용자가 실제로 이 방에 참여 중인지 확인
        ChatPart chatPart = chatPartRepository.findByChatRoomAndUser(chatRoom, user).orElseThrow(()->new EntityNotFoundException("CHATROOM_MEMBER_NOT_FOUND"));

        // MongoDB에 저장된 메시지 중 (roomId가 동일 && 메세지 시간이 "입장 시점 이후"인 것)만 조회
        List<Message> chats = messageRepository.findAllByRoomIdAndTimeAfter(roomId, chatPart.getEnterTime());

        // 메시지 -> DTO 변환
        return chats.stream()
                .map(chat -> modelMapper.map(chat, MessageDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String getAuthorLoginEmail(Long boardId) {
        // 1. boardId로 Board 객체 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("BOARD_NOT_FOUND"));

        // 2. Board 객체에서 User 객체 직접 접근
        User user = board.getUser();
        if (user == null) {
            throw new EntityNotFoundException("USER_NOT_FOUND");
        }

        // 3. User 객체에서 로그인 이메일 추출
        return user.getLoginEmail();
    }
}
