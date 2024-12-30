package com.semoi.semo.board.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.semoi.semo.board.dto.requestdto.BoardRequestDto;
import com.semoi.semo.board.dto.responsedto.BoardListResponseDto;
import com.semoi.semo.board.entity.Board;
import com.semoi.semo.board.repository.BoardRepository;
import com.semoi.semo.global.exception.DataNotFoundException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class BoardServiceTest {

    private final BoardRepository boardRepository = Mockito.mock(BoardRepository.class);
    private final BoardService boardService = new BoardService(boardRepository);

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this); // Mockito 초기화
    }

    @Disabled
    @Test
    void testGetAllBoards() {
        // Mock 데이터 설정
        Board mockBoard1 = new Board();
        mockBoard1.setBoardId(1L);
        mockBoard1.setTitle("First Board");
        mockBoard1.setHit(100);
        mockBoard1.setRecruitmentType("Full-time");
        mockBoard1.setRecruitmentCount(5);
        mockBoard1.setRecruitmentField("Engineering");
        mockBoard1.setRecruitmentMethod("Online");
        mockBoard1.setRecruitmentDeadline(LocalDateTime.now().plusDays(7));
        mockBoard1.setProgressPeriod("3 months");
        mockBoard1.setCreatedAt(LocalDateTime.now());
        mockBoard1.setUpdatedAt(LocalDateTime.now());

        Board mockBoard2 = new Board();
        mockBoard2.setBoardId(2L);
        mockBoard2.setTitle("Second Board");
        mockBoard2.setHit(200);
        mockBoard2.setRecruitmentType("Part-time");
        mockBoard2.setRecruitmentCount(2);
        mockBoard2.setRecruitmentField("Marketing");
        mockBoard2.setRecruitmentMethod("Offline");
        mockBoard2.setRecruitmentDeadline(LocalDateTime.now().plusDays(14));
        mockBoard2.setProgressPeriod("1 month");
        mockBoard2.setCreatedAt(LocalDateTime.now());
        mockBoard2.setUpdatedAt(null);

        List<Board> mockBoardList = Arrays.asList(mockBoard1, mockBoard2);

        // Mock Page 객체 생성
        Pageable pageable = PageRequest.of(0, 2);
        Page<Board> mockPage = new PageImpl<>(mockBoardList, pageable, mockBoardList.size());

        // Mock Repository 반환값 설정
        when(boardRepository.findAllActiveBoards(any(Pageable.class))).thenReturn(mockPage);

        // Service 호출
        Page<BoardListResponseDto> boardDtos = boardService.getAllBoards(pageable);

        // 검증
        assertThat(boardDtos).isNotNull();
        assertThat(boardDtos.getTotalElements()).isEqualTo(2);
        assertThat(boardDtos.getTotalPages()).isEqualTo(1);

        // 첫 번째 게시글 검증
        BoardListResponseDto firstDto = boardDtos.getContent().get(0);
        assertThat(firstDto.getBoardId()).isEqualTo(1L);
        assertThat(firstDto.getTitle()).isEqualTo("First Board");
        assertThat(firstDto.getRecruitmentType()).isEqualTo("Full-time");

        // 두 번째 게시글 검증
        BoardListResponseDto secondDto = boardDtos.getContent().get(1);
        assertThat(secondDto.getBoardId()).isEqualTo(2L);
        assertThat(secondDto.getTitle()).isEqualTo("Second Board");
        assertThat(secondDto.getRecruitmentType()).isEqualTo("Part-time");
        assertThat(secondDto.getUpdatedAt()).isNull();
    }

    @Test
    void testCreateBoard() {
        // Given: Request DTO 생성
        BoardRequestDto requestDto = new BoardRequestDto();
        requestDto.setTitle("Test Title");
        requestDto.setContent("Test Content");
        requestDto.setRecruitmentType("Backend");
        requestDto.setRecruitmentCount(5L);
        requestDto.setRecruitmentField("Spring");
        requestDto.setRecruitmentMethod("Online");
        requestDto.setRecruitmentDeadline(OffsetDateTime.now().plusDays(7));
        requestDto.setProgressPeriod("3 months");

        // When: Service 호출
        boardService.createBoard(requestDto);

        // Then: Repository의 save 메서드 호출 확인
        ArgumentCaptor<Board> captor = ArgumentCaptor.forClass(Board.class);
        verify(boardRepository).save(captor.capture());

        // 저장된 Board 엔티티 검증
        Board savedBoard = captor.getValue();
        assertThat(savedBoard.getTitle()).isEqualTo("Test Title");
        assertThat(savedBoard.getRecruitmentType()).isEqualTo("Backend");
        assertThat(savedBoard.getHit()).isEqualTo(0); // 기본값 확인
    }

    @Test
    void testUpdateBoard() {
        // Given: 기존 게시물 Mock 데이터
        Board existingBoard = new Board();
        existingBoard.setBoardId(1L);
        existingBoard.setTitle("Old Title");
        existingBoard.setContent("Old Content");

        when(boardRepository.findById(1L)).thenReturn(Optional.of(existingBoard));

        // Given: 수정 요청 DTO
        BoardRequestDto requestDto = new BoardRequestDto();
        requestDto.setTitle("New Title");
        requestDto.setContent("Updated Content");
        requestDto.setRecruitmentType("Updated Type");
        requestDto.setRecruitmentCount(10L);
        requestDto.setRecruitmentField("Updated Field");
        requestDto.setRecruitmentMethod("Updated Method");
        requestDto.setRecruitmentDeadline(OffsetDateTime.now().plusDays(7));
        requestDto.setProgressPeriod("6 months");

        // When: 수정 메서드 호출
        boardService.updateBoard(1L, requestDto);

        // Then: 저장 확인
        ArgumentCaptor<Board> captor = ArgumentCaptor.forClass(Board.class);
        verify(boardRepository).save(captor.capture()); // save 호출 확인

        // 캡처된 객체 검증
        Board updatedBoard = captor.getValue();
        assertThat(updatedBoard.getTitle()).isEqualTo("New Title");
        assertThat(updatedBoard.getContent()).isEqualTo("Updated Content");
        assertThat(updatedBoard.getRecruitmentType()).isEqualTo("Updated Type");
    }


    @Test
    void testUpdateBoard_NotFound() {
        // Given: 게시물이 없는 경우
        when(boardRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then: 예외 발생 확인
        assertThrows(DataNotFoundException.class, () -> {
            boardService.updateBoard(999L, new BoardRequestDto());
        });

        // save 호출되지 않음을 검증
        verify(boardRepository, never()).save(any(Board.class));
    }

    @Test
    void testSoftDeleteBoard() {
        // Given: Mock 데이터
        Board existingBoard = new Board();
        existingBoard.setBoardId(1L);
        existingBoard.setTitle("Test Title");
        existingBoard.setDeletedAt(null); // 삭제되지 않은 상태

        when(boardRepository.findById(1L)).thenReturn(Optional.of(existingBoard));

        // When: 삭제 호출
        boardService.softDeleteBoard(1L);

        // Then: 삭제 시간 설정 확인
        verify(boardRepository).save(existingBoard);
        assertThat(existingBoard.getDeletedAt()).isNotNull(); // 삭제 시간이 설정되었는지 확인
    }

    @Test
    void testFindAllActiveBoards() {
        // Given: Mock 데이터 생성
        Board activeBoard = new Board();
        activeBoard.setBoardId(1L);
        activeBoard.setTitle("Active Board");
        activeBoard.setDeletedAt(null);

        Board deletedBoard = new Board();
        deletedBoard.setBoardId(2L);
        deletedBoard.setTitle("Deleted Board");
        deletedBoard.setDeletedAt(LocalDateTime.now());

        List<Board> mockBoards = List.of(activeBoard, deletedBoard);
        Page<Board> mockPage = new PageImpl<>(List.of(activeBoard), PageRequest.of(0, 10), 1);

        // When: Repository에서 활성 데이터만 반환하도록 설정
        when(boardRepository.findAllActiveBoards(any(Pageable.class))).thenReturn(mockPage);

        // Then: 활성 데이터만 반환되는지 확인
        Pageable pageable = PageRequest.of(0, 10);
        Page<BoardListResponseDto> result = boardService.getAllBoards(pageable);

        assertThat(result.getContent()).hasSize(1); // 활성 게시물만 포함
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Active Board");
    }
}
