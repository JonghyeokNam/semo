package com.semoi.semo.bookmark.service;

import com.semoi.semo.applyForm.repository.ApplyFormRepository;
import com.semoi.semo.board.dto.responsedto.BoardResponseDto;
import com.semoi.semo.board.entity.Board;
import com.semoi.semo.board.repository.BoardRepository;
import com.semoi.semo.bookmark.domain.Bookmark;
import com.semoi.semo.bookmark.repository.BookmarkRepository;
import com.semoi.semo.comment.repository.CommentRepository;
import com.semoi.semo.global.exception.ErrorCode;
import com.semoi.semo.global.exception.SemoException;
import com.semoi.semo.user.domain.User;
import com.semoi.semo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ApplyFormRepository applyFormRepository;
    private final CommentRepository commentRepository;

    @Override
    // 북마크 상태 조회
    public Boolean getState(String loginEmail, Long boardId) {
        User user = getUserByEmailOrElseThrow(loginEmail);
        Board board = getBoardByIdOrElseThrow(boardId);

        return bookmarkRepository.findByUserAndBoard(user, board).isPresent();
    }

    @Override
    // 북마크 추가 혹은 삭제
    public void addOrCancelBookmark(String loginEmail, Long boardId) {

        User user = getUserByEmailOrElseThrow(loginEmail);
        Board board = getBoardByIdOrElseThrow(boardId);

        Bookmark bookmark = bookmarkRepository.findByUserAndBoard(user, board).orElse(null);

        // 북마크 안한 경우, 추가
        if (bookmark == null) {
            bookmark = Bookmark.create(user, board);
            bookmarkRepository.save(bookmark);
            log.info(boardId + "번 게시물 북마크 등록");
        } else {
        // 북마크 되어 있는 경우, 취소
            bookmarkRepository.delete(bookmark);
            log.info(boardId + "번 게시물 북마크 취소");
        }
    }

    // 해당 유저의 모든 북마크 게시글 목록 조회
    public List<BoardResponseDto> getBookmarksFromUser(String loginEmail) {
        User user = getUserByEmailOrElseThrow(loginEmail);

        List<Bookmark> bookmarks = bookmarkRepository.findAllByUserOrderByCreatedAtDesc(user);

        List<BoardResponseDto> boardListDtos = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            Board board = boardRepository.findById(bookmark.getBoard().getBoardId())
                    .orElse(null);

            if (board == null) {
                continue;
            }

            boolean isBookmarked = getState(loginEmail, board.getBoardId());

            // 댓글 수 조회
            int commentCount = commentRepository.countByBoardId(board.getBoardId());

            // ApplyFormRepository에서 boardId와 userId를 기준으로 존재 여부 확인
            boolean isParticipated = applyFormRepository.existsByBoardIdAndUserId(board.getBoardId(), user.getUserId());

            BoardResponseDto boardResponseDto = BoardResponseDto.fromEntity(board, loginEmail, isParticipated, isBookmarked, applyFormRepository, commentCount);

            boardListDtos.add(boardResponseDto);
        }

        return boardListDtos;
    }

    // 로그인 이메일로 유저 조회 혹은 익셉션
    private User getUserByEmailOrElseThrow(String loginEmail) {
        return userRepository.findByLoginEmail(loginEmail)
                .orElseThrow(() -> new SemoException(ErrorCode.USER_NOT_FOUND));
    }

    // 보드 조회 혹은 익셉션
    private Board getBoardByIdOrElseThrow(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new SemoException(ErrorCode.BOARD_NOT_FOUND));
    }

}
