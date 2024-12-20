package com.semoi.semo.board.mapper;

import com.semoi.semo.board.dto.responsedto.BoardListResponseDto;
import com.semoi.semo.board.dto.responsedto.BoardResponseDto;
import com.semoi.semo.board.entity.Board;

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
}