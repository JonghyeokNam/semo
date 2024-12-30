package com.semoi.semo.board.repository;

import com.semoi.semo.board.entity.Board;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long>{

    @Query("SELECT b FROM Board b WHERE b.deletedAt IS NULL")
    Page<Board> findAllActiveBoards(Pageable pageable);

    Page<Board> findAll(Pageable pageable);

    // 특정 사용자가 작성한 게시글만 조회
    List<Board> findByUserId(Long userId);
}