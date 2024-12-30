package com.semoi.semo.comment.service;

import com.semoi.semo.board.entity.Board;
import com.semoi.semo.board.repository.BoardRepository;
import com.semoi.semo.comment.domain.Comment;
import com.semoi.semo.comment.dto.CommentRequestDto;
import com.semoi.semo.comment.dto.CommentResponseDto;
import com.semoi.semo.comment.repository.CommentRepository;
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
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public List<CommentResponseDto> getAllComments(Long boardId) {
        Board board = getBoardOrException(boardId);

        return commentRepository.findAllByBoard(board)
                .stream().map(CommentResponseDto::of).toList();
    }

    @Override
    public void addComment(String loginEmail, Long boardId, CommentRequestDto commentRequestDto) {
        Board board = getBoardOrException(boardId);
        User user = getUserOrException(loginEmail);

        Comment comment = Comment.create(commentRequestDto.content(), user, board);
        commentRepository.save(comment);
    }

    @Override
    public void updateComment(String loginEmail, Long commentId, CommentRequestDto commentRequestDto) {

        Comment comment = getCommentOwnerOrException(loginEmail, commentId);
        comment.update(commentRequestDto.content());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(String loginEmail, Long commentId) {
        Comment comment = getCommentOwnerOrException(loginEmail, commentId);
        commentRepository.delete(comment);
    }

    private Comment getCommentOwnerOrException(String loginEmail, Long commentId) {
        User user = getUserOrException(loginEmail);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new SemoException(ErrorCode.COMMENT_NOT_FOUND));

        if (comment.getUser() != user) {
            throw new SemoException(ErrorCode.DUPLICATED_USER_EMAIL);
        }

        return comment;
    }

    private Board getBoardOrException(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new SemoException(ErrorCode.BOARD_NOT_FOUND));
    }

    private User getUserOrException(String loginEmail) {
        return userRepository.findByLoginEmail(loginEmail)
                .orElseThrow(() -> new SemoException(ErrorCode.USER_NOT_FOUND));
    }
}
