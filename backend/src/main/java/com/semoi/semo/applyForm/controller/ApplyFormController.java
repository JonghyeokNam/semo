package com.semoi.semo.applyForm.controller;

import com.semoi.semo.applyForm.dto.requestdto.ApplyFormRequestDto;
import com.semoi.semo.applyForm.dto.responsedto.ApplyFormListResponseDto;
import com.semoi.semo.applyForm.dto.responsedto.ApplyFormResponseDto;
import com.semoi.semo.applyForm.service.ApplyFormService;
import com.semoi.semo.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @PathVariable("boardId") Long boardId,
            HttpServletRequest request
    ) {
        List<ApplyFormListResponseDto> applyForms = applyFormService.getApplyFormsByBoardId(boardId, request);
        return Response.success(applyForms);
    }

    @Operation(summary = "사용자 신청서 목록 조회", description = "현재 사용자의 모든 신청서를 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/user/applyforms")
    public Response<List<ApplyFormListResponseDto>> getUserApplyForms(
            HttpServletRequest request) {
        List<ApplyFormListResponseDto> applyForms = applyFormService.getUserApplyForms(request);
        return Response.success(applyForms);
    }

    @Operation(summary = "신청서 생성", description = "특정 게시글에 대한 신청서를 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/boards/{boardId}/applyform")
    public Response<Void> createApplyForm(
            @PathVariable("boardId") Long boardId,
            @RequestBody ApplyFormRequestDto requestDto,
            HttpServletRequest request
    ) {
        applyFormService.createApplyForm(boardId, requestDto, request);
        return Response.success();
    }

    @Operation(summary = "신청서 조회", description = "사용자가 특정 신청서를 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "신청서 조회 성공"),
            @ApiResponse(responseCode = "404", description = "신청서를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/user/applyforms/{applyFormId}")
    public Response<ApplyFormResponseDto> getUserApplyForm(
            @PathVariable("applyFormId") Long applyFormId,
            HttpServletRequest request
    ) {
        ApplyFormResponseDto applyForm = applyFormService.getUserApplyForm(applyFormId, request);
        return Response.success(applyForm);
    }

    @Operation(summary = "신청서 수정", description = "사용자가 특정 신청서를 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "신청서 수정 성공"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PutMapping("/user/applyforms/{applyFormId}")
    public Response<Void> updateApplyForm(
            @PathVariable("applyFormId") Long applyFormId,
            @RequestBody ApplyFormRequestDto updateDto,
            HttpServletRequest request
    ) {
        applyFormService.updateUserApplyForm(applyFormId, updateDto, request);
        return Response.success();
    }

    @Operation(summary = "신청서 삭제", description = "사용자가 특정 신청서를 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "신청서 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "신청서를 찾을 수 없음"),
            @ApiResponse(responseCode = "403", description = "삭제 권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping("/user/applyforms/{applyFormId}")
    public Response<Void> deleteApplyForm(
            @PathVariable("applyFormId") Long applyFormId,
            HttpServletRequest request
    ) {
        applyFormService.deleteApplyForm(applyFormId, request);
        return Response.success();
    }
}
