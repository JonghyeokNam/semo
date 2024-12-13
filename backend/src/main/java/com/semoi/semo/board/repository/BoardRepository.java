package com.semoi.semo.board.repository;

import com.semoi.semo.board.entity.Board;
import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>{

}