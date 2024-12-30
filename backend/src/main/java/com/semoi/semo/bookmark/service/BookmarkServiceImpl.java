package com.semoi.semo.bookmark.service;

import com.semoi.semo.board.entity.Board;
import com.semoi.semo.board.repository.BoardRepository;
import com.semoi.semo.bookmark.domain.Bookmark;
import com.semoi.semo.bookmark.dto.BookmarkResponseDto;
import com.semoi.semo.bookmark.repository.BookmarkRepository;
import com.semoi.semo.global.exception.ErrorCode;
import com.semoi.semo.global.exception.SemoException;
import com.semoi.semo.user.domain.User;
import com.semoi.semo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public void addOrCancelBookmark(String loginEmail, Long boardId) {

        User user = getUserByEmailOrElseThrow(loginEmail);
        Board board = getBoardByIdOrElseThrow(boardId);

        Bookmark bookmark = bookmarkRepository.findByUserAndBoard(user, board).orElse(null);
        
        // 북마크 안한 경우, 추가
        if (bookmark == null) {
            bookmark = Bookmark.create(user, board);
            bookmarkRepository.save(bookmark);
        } else {
        // 북마크 되어 있는 경우, 취소
            bookmarkRepository.delete(bookmark);
        }
    }

    public List<BookmarkResponseDto> getBookmarksFromUser(String loginEmail) {
        User user = getUserByEmailOrElseThrow(loginEmail);

        return bookmarkRepository.findAllByUserOrderByCreatedAtDesc(user)
                .stream().map(BookmarkResponseDto::of).toList();
    }

    private User getUserByEmailOrElseThrow(String loginEmail) {
        return userRepository.findByLoginEmail(loginEmail)
                .orElseThrow(() -> new SemoException(ErrorCode.USER_NOT_FOUND));
    }

    private Board getBoardByIdOrElseThrow(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new SemoException(ErrorCode.BOARD_NOT_FOUND));
    }

}
