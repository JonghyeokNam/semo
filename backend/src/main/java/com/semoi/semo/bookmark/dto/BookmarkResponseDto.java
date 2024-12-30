package com.semoi.semo.bookmark.dto;

import com.semoi.semo.bookmark.domain.Bookmark;

import java.time.LocalDateTime;

public record BookmarkResponseDto(
        Long bookmarkId,
        Long userId,
        Long boardId,
        LocalDateTime createdAt
) {
    public static BookmarkResponseDto of(Bookmark bookmark) {
        return new BookmarkResponseDto(
                bookmark.getBookmarkId(),
                bookmark.getUser().getUserId(),
                bookmark.getBoard().getBoardId(),
                bookmark.getCreatedAt()
        );
    }
}
