package com.semoi.semo.applyForm.controller;

import com.semoi.semo.applyForm.dto.responsedto.ApplyFormListResponseDto;
import com.semoi.semo.applyForm.service.ApplyFormService;
import com.semoi.semo.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "ApplyForm", description = "지원서 관련 API")
public class ApplyFormController {

    private final ApplyFormService applyFormService;

    @Operation(summary = "특정 게시글 신청 리스트 조회", description = "모집 게시글의 지원자들의 신청서를 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/boards/{boardId}/applyform")
    public Response<List<ApplyFormListResponseDto>> getApplyFormsByBoardId(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable("boardId") Long boardId) {
        List<ApplyFormListResponseDto> applyForms = applyFormService.getApplyFormsByBoardId(boardId);
        return Response.success(applyForms);
    }
}