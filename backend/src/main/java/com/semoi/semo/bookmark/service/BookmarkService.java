package com.semoi.semo.bookmark.service;

import com.semoi.semo.bookmark.dto.BookmarkResponseDto;

import java.util.List;

public interface BookmarkService {

    Boolean getState(String name, Long boardId);
    void addOrCancelBookmark(String loginEmail, Long boardId);
    List<BookmarkResponseDto> getBookmarksFromUser(String loginEmail);
}
