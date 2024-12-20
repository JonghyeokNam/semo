package com.semoi.semo.board.service;

import com.semoi.semo.board.dto.responsedto.BoardListResponseDto;
import com.semoi.semo.board.dto.responsedto.BoardResponseDto;
import com.semoi.semo.board.entity.Board;
import com.semoi.semo.board.mapper.BoardMapper;
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
                .map(BoardMapper::toBoardListResponseDto)
                .collect(Collectors.toList());
    }

    public BoardResponseDto getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new DataNotFoundException("board not found"));

        return BoardMapper.toBoardResponseDto(board);
    }
}
