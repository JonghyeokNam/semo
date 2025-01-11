package com.semoi.semo.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_USER_EMAIL(HttpStatus.CONFLICT, "User email is duplicated"),
    DUPLICATED_USER_NICKNAME(HttpStatus.CONFLICT, "User nickname is duplicated"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not founded"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Password is invalid"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token is invalid"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server error"),

    // Campus
    CAMPUS_NOT_FOUND(HttpStatus.NOT_FOUND, "Campus not found"),

    // Token
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "Token not found"),

    // notification
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND,  "Notification not found"),

    // Board
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "Board not found"),

    // Bookmark
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "Bookmark not found"),

    // comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Comment not found"),

    // position
    POSITION_NOT_FOUND(HttpStatus.NOT_FOUND, "Position not found"),

    // user
    UNSUITABLE_EMAIL(HttpStatus.BAD_REQUEST, "Email is invalid"),;

    private final HttpStatus status;
    private final String message;
}
