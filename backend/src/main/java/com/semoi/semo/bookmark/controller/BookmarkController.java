package com.semoi.semo.bookmark.controller;

import com.semoi.semo.bookmark.dto.BookmarkResponseDto;
import com.semoi.semo.bookmark.service.BookmarkServiceImpl;
import com.semoi.semo.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
@Slf4j
public class BookmarkController {

    private final BookmarkServiceImpl bookmarkService;

    @PostMapping("/boards/{boardId}")
    public Response<Void> addOrCancelBookmark(Authentication authentication, @PathVariable Long boardId) {
        bookmarkService.addOrCancelBookmark(authentication.getName(), boardId);
        return Response.success();
    }

    @GetMapping
    public Response<List<BookmarkResponseDto>> getBookmarks(Authentication authentication) {
        return Response.success(bookmarkService.getBookmarksFromUser(authentication.getName()));
    }

    @GetMapping("/boards/{boardId}")
    public Response<Boolean> getState(Authentication authentication, @PathVariable Long boardId) {
        return Response.success(bookmarkService.getState(authentication.getName(), boardId));
    }
}
