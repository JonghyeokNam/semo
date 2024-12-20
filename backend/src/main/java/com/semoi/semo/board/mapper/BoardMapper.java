package com.semoi.semo.board.mapper;

import com.semoi.semo.board.dto.requestdto.BoardRequestDto;
import com.semoi.semo.board.dto.responsedto.BoardListResponseDto;
import com.semoi.semo.board.dto.responsedto.BoardResponseDto;
import com.semoi.semo.board.entity.Board;
import java.time.LocalDateTime;

public class BoardMapper {

    // Board -> BoardListResponseDto 변환
    public static BoardListResponseDto toBoardListResponseDto(Board board) {
        return BoardListResponseDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .hit(board.getHit())
                .recruitmentType(board.getRecruitmentType())
                .recruitmentCount(board.getRecruitmentCount())
                .recruitmentField(board.getRecruitmentField())
                .recruitmentMethod(board.getRecruitmentMethod())
                .recruitmentDeadline(board.getRecruitmentDeadline())
                .progressPeriod(board.getProgressPeriod())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .author(BoardListResponseDto.AuthorDto.builder()
                        .userId(board.getUserId())
                        .username("Unknown")
                        .build())
                .build();
    }

    // Board -> BoardResponseDto 변환
    public static BoardResponseDto toBoardResponseDto(Board board) {
        return BoardResponseDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .hit(board.getHit())
                .recruitmentType(board.getRecruitmentType())
                .recruitmentCount(board.getRecruitmentCount())
                .recruitmentField(board.getRecruitmentField())
                .recruitmentMethod(board.getRecruitmentMethod())
                .recruitmentDeadline(board.getRecruitmentDeadline())
                .progressPeriod(board.getProgressPeriod())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .author(BoardResponseDto.AuthorDto.builder()
                        .userId(board.getUserId())
                        .username("Unknown")
                        .build())
                .build();
    }

    // Board Entity 생성
    public static Board toEntity(BoardRequestDto requestDto) {
        Board board = new Board();
        board.setTitle(requestDto.getTitle());
        board.setContent(requestDto.getContent());
        board.setRecruitmentType(requestDto.getRecruitmentType());
        board.setRecruitmentCount(requestDto.getRecruitmentCount().intValue());
        board.setRecruitmentField(requestDto.getRecruitmentField());
        board.setRecruitmentMethod(requestDto.getRecruitmentMethod());
        board.setRecruitmentDeadline(requestDto.getRecruitmentDeadline().toLocalDateTime());
        board.setProgressPeriod(requestDto.getProgressPeriod());
        board.setCreatedAt(LocalDateTime.now());
        board.setHit(0);
        return board;
    }
}