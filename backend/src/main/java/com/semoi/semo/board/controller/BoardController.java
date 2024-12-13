package com.semoi.semo.board.controller;

import com.semoi.semo.board.dto.responsedto.BoardListResponseDto;
import com.semoi.semo.board.service.BoardService;
import com.semoi.semo.common.response.Response;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public Response<List<BoardListResponseDto>> getBoardList(){
        List<BoardListResponseDto> boardList = boardService.getAllBoards();

        return Response.success(boardList);
    }

//    @GetMapping("/api/boards/{boardId}")
//    @ResponseBody
//    public String getBoard(){
//        return "게시물 목록 조회";
//    }
//
//    @GetMapping("/api/boards")
//    @ResponseBody
//    public String createBoard(){
//        return "게시물 생성";
//    }
//
//    @GetMapping("/api/boards/{boardId}")
//    @ResponseBody
//    public String updateBoard(){
//        return "게시물 수정";
//    }
//
//    @GetMapping("/api/boards/{boardId}")
//    @ResponseBody
//    public String deleteBoard(){
//        return "게시물 삭제";
//    }
}
