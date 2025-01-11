package com.semoi.semo.board.mapper;

import com.semoi.semo.board.dto.requestdto.BoardRequestDto;
import com.semoi.semo.board.dto.responsedto.BoardListResponseDto;
import com.semoi.semo.board.dto.responsedto.BoardResponseDto;
import com.semoi.semo.board.entity.Board;
import com.semoi.semo.user.domain.User;
import java.time.LocalDateTime;

public class BoardMapper {

    // Board -> BoardListResponseDto 변환
    public static BoardListResponseDto toBoardListResponseDto(Board board, Boolean isAuthor) {
        return BoardListResponseDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .hit(board.getHit())
                .recruitmentTypes(board.getRecruitmentTypes())
                .recruitmentCount(board.getRecruitmentCount())
                .recruitmentField(board.getRecruitmentField())
                .recruitmentMethod(board.getRecruitmentMethod())
                .recruitmentDeadline(board.getRecruitmentDeadline())
                .progressPeriod(board.getProgressPeriod())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .author(BoardListResponseDto.AuthorDto.builder()
                        .username(board.getUser().getUsername())
                        .isAuthor(isAuthor) // 작성자 여부 포함
                        .build())
                .build();
    }

    // Board -> BoardResponseDto 변환
    public static BoardResponseDto toBoardResponseDto(Board board, Boolean isAuthor) {
        return BoardResponseDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .hit(board.getHit())
                .recruitmentTypes(board.getRecruitmentTypes())
                .recruitmentCount(board.getRecruitmentCount())
                .recruitmentField(board.getRecruitmentField())
                .recruitmentMethod(board.getRecruitmentMethod())
                .recruitmentDeadline(board.getRecruitmentDeadline())
                .progressPeriod(board.getProgressPeriod())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .author(BoardResponseDto.AuthorDto.builder()
                        .username(board.getUser().getUsername())
                        .isAuthor(isAuthor) // 작성자 여부 포함
                        .build())
                .build();
    }

    // 생성 요청 DTO -> 엔티티
    public static Board toEntity(BoardRequestDto boardRequestDto, User user) {
        Board board = new Board();
        board.setTitle(boardRequestDto.getTitle());
        board.setContent(boardRequestDto.getContent());
        board.setRecruitmentTypes(boardRequestDto.getRecruitmentTypes());
        board.setRecruitmentCount(boardRequestDto.getRecruitmentCount().intValue());
        board.setRecruitmentField(boardRequestDto.getRecruitmentField());
        board.setRecruitmentMethod(boardRequestDto.getRecruitmentMethod());
        board.setRecruitmentDeadline(boardRequestDto.getRecruitmentDeadline().toLocalDateTime());
        board.setProgressPeriod(boardRequestDto.getProgressPeriod());
        board.setCreatedAt(LocalDateTime.now());
        board.setHit(0);
        board.setUser(user); // 작성자 설정
        return board;
    }

    // 수정 요청 DTO -> 기존 엔티티 수정
    public static Board updateEntity(Board board, BoardRequestDto boardRequestDto) {
        board.setTitle(boardRequestDto.getTitle());
        board.setContent(boardRequestDto.getContent());
        board.setRecruitmentTypes(boardRequestDto.getRecruitmentTypes());
        board.setRecruitmentCount(boardRequestDto.getRecruitmentCount().intValue());
        board.setRecruitmentField(boardRequestDto.getRecruitmentField());
        board.setRecruitmentMethod(boardRequestDto.getRecruitmentMethod());
        board.setRecruitmentDeadline(boardRequestDto.getRecruitmentDeadline().toLocalDateTime());
        board.setProgressPeriod(boardRequestDto.getProgressPeriod());
        board.setUpdatedAt(LocalDateTime.now()); // 수정 시 현재 시간으로 설정
        return board;
    }
}