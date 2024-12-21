package com.semoi.semo.board.controller;

import com.semoi.semo.board.dto.responsedto.BoardListResponseDto;
import com.semoi.semo.board.service.BoardService;
import com.semoi.semo.global.response.Response;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Response<List<BoardListResponseDto>> getBoardList(){
        List<BoardListResponseDto> boardList = boardService.getAllBoards();

        return Response.success(boardList);
    }

    @Operation(summary = "단일 게시물 조회", description = "특정 ID의 게시물을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "게시물을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{boardId}")
//    public Response<BoardListResponseDto> getBoardById(
//            @Parameter(description = "게시물 ID", example = "1") @PathVariable Long boardId) {
//        BoardListResponseDto board = boardService.getBoardById(boardId);
//        return Response.success(board);
//    }
    public Response<Void> getBoardById(
            @Parameter(description = "게시물 ID", example = "1") @PathVariable Long boardId) {
        return Response.success();
    }

    @Operation(summary = "게시물 생성", description = "새로운 게시물을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public Response<Void> createBoard(
            @RequestBody BoardListResponseDto boardRequest) {
//        boardService.createBoard(boardRequest);
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
            @Parameter(description = "게시물 ID", example = "1") @PathVariable Long id,
            @RequestBody BoardListResponseDto boardRequest) {
//        boardService.updateBoard(id, boardRequest);
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
            @Parameter(description = "게시물 ID", example = "1") @PathVariable Long id) {
//        boardService.deleteBoard(id);
        return Response.success();
    }
}