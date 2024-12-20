package com.semoi.semo.board.service;

import com.semoi.semo.board.dto.responsedto.BoardListResponseDto;
import com.semoi.semo.board.dto.responsedto.BoardResponseDto;
import com.semoi.semo.board.entity.Board;
import com.semoi.semo.board.mapper.BoardMapper;
import com.semoi.semo.board.repository.BoardRepository;
import com.semoi.semo.common.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<BoardListResponseDto> getAllBoards(Pageable pageable) {
        return boardRepository.findAll(pageable)
                .map(BoardMapper::toBoardListResponseDto);
    }

    public BoardResponseDto getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new DataNotFoundException("board not found"));

        return BoardMapper.toBoardResponseDto(board);
    }
}
