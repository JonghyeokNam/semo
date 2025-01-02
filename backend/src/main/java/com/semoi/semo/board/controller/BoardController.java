package com.semoi.semo.board.controller;

import com.semoi.semo.board.dto.requestdto.BoardRequestDto;
import com.semoi.semo.board.dto.responsedto.BoardListResponseDto;
import com.semoi.semo.board.dto.responsedto.BoardResponseDto;
import com.semoi.semo.board.service.BoardService;
import com.semoi.semo.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시물 목록 조회", description = "모든 게시물을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping
    public Response<Page<BoardListResponseDto>> getBoardList(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            HttpServletRequest request
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<BoardListResponseDto> boardList = boardService.getAllBoards(pageable, request);

        return Response.success(boardList);
    }

    @Operation(summary = "단일 게시물 조회", description = "특정 ID의 게시물을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "게시물을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{boardId}")
    public Response<BoardResponseDto> getBoardById(
            @Parameter(description = "게시물 ID", example = "1")
            @PathVariable("boardId") Long boardId,
            HttpServletRequest request) {
        BoardResponseDto board = boardService.getBoardById(boardId, request);
        return Response.success(board);
    }

    @Operation(summary = "게시물 생성", description = "새로운 게시물을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "생성 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public Response<Void> createBoard(
            @Valid @RequestBody BoardRequestDto boardRequestDto,
            HttpServletRequest request) {
        boardService.createBoard(boardRequestDto, request);
        return Response.success();
    }

    @Operation(summary = "게시물 수정", description = "기존 게시물을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "게시물을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PutMapping("/{boardId}")
    public Response<Void> updateBoard(
            @Parameter(description = "게시물 ID", example = "1")
            @PathVariable("boardId") Long boardId,
            @Valid @RequestBody BoardRequestDto boardRequestDto,
            HttpServletRequest request) {
        boardService.updateBoard(boardId, boardRequestDto, request);
        return Response.success();
    }

    @Operation(summary = "게시물 삭제", description = "특정 게시물을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "게시물을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping("/{boardId}")
    public Response<Void> deleteBoard(
            @Parameter(description = "게시물 ID", example = "1")
            @PathVariable("boardId") Long boardId,
            HttpServletRequest request) {
        boardService.softDeleteBoard(boardId, request);
        return Response.success();
    }

    @Operation(summary = "사용자 작성 게시글 조회", description = "사용자가 작성한 게시글 목록을 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
            @ApiResponse(responseCode = "404", description = "게시글이 존재하지 않음")
    })
    @GetMapping("/myboards")
    public Response<List<BoardListResponseDto>> getMyBoards(HttpServletRequest request) {
        // Service 호출
        List<BoardListResponseDto> myBoards = boardService.getMyBoards(request);

        // 결과 반환
        return Response.success(myBoards);
    }
}