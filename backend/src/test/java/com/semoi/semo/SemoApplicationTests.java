package com.semoi.semo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.semoi.semo.board.entity.Board;
import com.semoi.semo.board.repository.BoardRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SemoApplicationTests {

	@Autowired
	private BoardRepository boardRepository;

	@Disabled("board 테이블 테스트 데이터 생성기")
	@Test
	void testSaveAndFindBoard() {
		Board board = new Board();
		board.setTitle("안녕하세요, 제 이름 대통령상");
		board.setContent("대통령상이랑 프로젝트 할 사람 구합니다.");
		board.setCreatedAt(LocalDateTime.now());
		board.setUpdatedAt(null);
		board.setDeletedAt(null);
		board.setHit(0);
		board.setUserId(1L);
		board.setRecruitmentType("백엔드");
		board.setRecruitmentCount(5);
		board.setRecruitmentField("Spring");
		board.setRecruitmentMethod("Online");
		board.setRecruitmentDeadline(LocalDateTime.now().plusDays(7)); // 7일 뒤
		board.setProgressPeriod("3개월 ~ 6개월");

		Board savedBoard = this.boardRepository.save(board);
		Board foundBoard = this.boardRepository.findById(savedBoard.getBoardId()).orElse(null);

		assertThat(foundBoard).isNotNull();
		assertThat(foundBoard.getTitle()).isEqualTo("안녕하세요, 제 이름 대통령상");
		assertThat(foundBoard.getContent()).isEqualTo("대통령상이랑 프로젝트 할 사람 구합니다.");
	}

	@Disabled("board 테이블 테스트 데이터 업데이트 테스트")
	@Test
	void testUpdateBoard() {
		Optional<Board> optionalBoard = this.boardRepository.findById(1L);
		assertTrue(optionalBoard.isPresent());
		Board board = optionalBoard.get();
		board.setTitle("아무도 오지 마세요");

		Board savedBoard = this.boardRepository.save(board);

		assertThat(savedBoard).isNotNull();
		assertThat(savedBoard.getTitle()).isEqualTo("아무도 오지 마세요");
	}

	@Disabled("board 테이블 테스트 데이터 삭제 테스트")
	@Test
	void testDeleteBoard() {
		long previousBoardCount = this.boardRepository.count();
		Optional<Board> optionalBoard = this.boardRepository.findById(1L);
		assertTrue(optionalBoard.isPresent());
		Board board = optionalBoard.get();
		this.boardRepository.delete(board);
		long currentBoardCount = this.boardRepository.count();

		assertThat(previousBoardCount-1).isEqualTo(currentBoardCount);

	}
}
