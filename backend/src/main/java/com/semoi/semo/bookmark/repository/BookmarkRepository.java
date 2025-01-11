package com.semoi.semo.bookmark.repository;

import com.semoi.semo.board.entity.Board;
import com.semoi.semo.bookmark.domain.Bookmark;
import com.semoi.semo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserAndBoard(User user, Board board);

    List<Bookmark> findAllByUserOrderByCreatedAtDesc(User user);
}
