package com.semoi.semo.board.service;

import com.semoi.semo.board.dto.responsedto.BoardListResponseDto;
import com.semoi.semo.board.dto.responsedto.BoardResponseDto;
import com.semoi.semo.board.entity.Board;
import com.semoi.semo.board.repository.BoardRepository;
import com.semoi.semo.common.exception.DataNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardListResponseDto> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(board -> BoardListResponseDto.builder()
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
                        .build())
                .collect(Collectors.toList());
    }

    public BoardResponseDto getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new DataNotFoundException("board not found"));

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
