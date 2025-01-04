package com.semoi.semo.board.service;

import com.semoi.semo.applyForm.repository.ApplyFormRepository;
import com.semoi.semo.board.dto.requestdto.BoardRequestDto;
import com.semoi.semo.board.dto.responsedto.BoardListResponseDto;
import com.semoi.semo.board.dto.responsedto.BoardResponseDto;
import com.semoi.semo.board.entity.Board;
import com.semoi.semo.board.mapper.BoardMapper;
import com.semoi.semo.board.repository.BoardRepository;
import com.semoi.semo.comment.repository.CommentRepository;
import com.semoi.semo.global.exception.DataNotFoundException;
import com.semoi.semo.jwt.service.TokenProvider;
import com.semoi.semo.user.domain.User;
import com.semoi.semo.user.repository.UserRepository;
import com.semoi.semo.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ApplyFormRepository applyFormRepository;
    private final CommentRepository commentRepository;
    private final TokenProvider tokenProvider;
    private final UserService userService;

    public Page<BoardListResponseDto> getAllBoards(Pageable pageable, HttpServletRequest request) {

        // TokenProvider를 사용해 사용자 이메일 추출
        String userEmail = tokenProvider.getUserLoginEmail(request);

        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Order.desc("createdAt")) // "createdAt" 기준으로 역순 정렬
        );

        // 게시물 목록 조회 후 DTO 변환
        return boardRepository.findAllActiveBoards(sortedPageable)
                .map(board -> {
                    User user = userRepository.findByLoginEmail(userEmail)
                            .orElseThrow(() -> new DataNotFoundException("User not found"));

                    // ApplyFormRepository에서 boardId와 userId를 기준으로 존재 여부 확인
                    boolean isParticipated = applyFormRepository.existsByBoardIdAndUserId(board.getBoardId(), user.getUserId());

                    return BoardListResponseDto.fromEntity(board, userEmail, isParticipated, applyFormRepository, commentRepository);
                });
    }

    public BoardResponseDto getBoardById(Long boardId, HttpServletRequest request) {

        // TokenProvider를 사용해 사용자 이메일 추출
        String userEmail = tokenProvider.getUserLoginEmail(request);

        // 사용자 조회
        User user = userRepository.findByLoginEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // 게시글 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new DataNotFoundException("board not found"));

        // 작성자 여부 확인
        boolean isAuthor = board.getUser().getLoginEmail().equals(user.getLoginEmail());

        // ApplyFormRepository에서 boardId와 userId를 기준으로 존재 여부 확인
        boolean isParticipated = applyFormRepository.existsByBoardIdAndUserId(board.getBoardId(), user.getUserId());

        return BoardResponseDto.fromEntity(board, userEmail, isParticipated, applyFormRepository, commentRepository);
    }

    public void createBoard(BoardRequestDto boardRequestDto, HttpServletRequest request) {

        // TokenProvider를 사용해 사용자 이메일 추출
        String userEmail = tokenProvider.getUserLoginEmail(request);

        // 사용자 조회
        User user = userRepository.findByLoginEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Board board = BoardMapper.toEntity(boardRequestDto, user);
        boardRepository.save(board);
        
        // 모집 점수 추가
        userService.updateUserScore(user, 10, 0);
    }

    public void updateBoard(Long boardId, BoardRequestDto boardRequestDto, HttpServletRequest request) {

        // TokenProvider를 사용해 사용자 이메일 추출
        String userEmail = tokenProvider.getUserLoginEmail(request);

        // 사용자 조회
        User user = userRepository.findByLoginEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new DataNotFoundException("board not found"));

        // 작성자 여부 확인
        boolean isAuthor = board.getUser().getLoginEmail().equals(userEmail);

        // 이게 아니라 프론트로 알려줘야할 거 같은데..
        if (!isAuthor) {
            throw new DataNotFoundException("You are not the author of this board.");
        }

        BoardMapper.updateEntity(board, boardRequestDto);
        boardRepository.save(board);
    }

    public void softDeleteBoard(Long boardId, HttpServletRequest request) {

        // TokenProvider를 사용해 사용자 이메일 추출
        String userEmail = tokenProvider.getUserLoginEmail(request);

        User user = userRepository.findByLoginEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException(("board not found")));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new DataNotFoundException(("board not found")));

        // 작성자 여부 확인
        boolean isAuthor = board.getUser().getLoginEmail().equals(userEmail);

        // 이게 아니라 프론트로 알려줘야할 거 같은데..
        if (!isAuthor) {
            throw new DataNotFoundException("You are not the author of this board.");
        }

        board.setDeletedAt(LocalDateTime.now());
        boardRepository.save(board);
    }

    public List<BoardListResponseDto> getMyBoards(HttpServletRequest request) {

        // 1. TokenProvider를 사용해 사용자 이메일 추출
        String userEmail = tokenProvider.getUserLoginEmail(request);

        // 2. 유효하지 않은 토큰 처리
        if (userEmail == null) {
            throw new DataNotFoundException("Invalid or missing token.");
        }

        // 3. 사용자 이메일 기반 게시글 조회
        List<Board> boards = boardRepository.findByUser_LoginEmail(userEmail);

        // 4. 본인 글을 참여하진 않으니 false
        boolean isParticipated = false;

        // 4. 게시글 목록을 DTO로 변환
        return boards.stream()
                .map(board -> BoardListResponseDto.fromEntity(board, userEmail, isParticipated, applyFormRepository, commentRepository)) // isAuthor는 항상 true
                .collect(Collectors.toList());
    }
}
