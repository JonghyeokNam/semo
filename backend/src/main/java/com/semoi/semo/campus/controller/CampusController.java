package com.semoi.semo.campus.controller;

import com.semoi.semo.campus.dto.response.CampusActScoreResponseDto;
import com.semoi.semo.campus.dto.response.CampusRecScoreResponseDto;
import com.semoi.semo.campus.dto.response.CampusResponseDto;
import com.semoi.semo.campus.dto.response.CourseResponseDto;
import com.semoi.semo.campus.service.CampusService;
import com.semoi.semo.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/campuses")
@Slf4j
@RequiredArgsConstructor
public class CampusController {

    private final CampusService campusService;

    @Operation(
            summary = "연도별 참여 순위 조회",
            description = "특정 연도의 캠퍼스 참여 점수를 기반으로 순위를 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "요청 성공 - 참여 순위 데이터 반환",
                    content = @Content(schema = @Schema(implementation = CampusActScoreResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/act/{year}")
    public Response<List<CampusActScoreResponseDto>> getActRanking(@PathVariable("year") int year) {
        return Response.success(campusService.getCampusActRankingByYear(year));
    }

    @Operation(
            summary = "연도별 모집 순위 조회",
            description = "특정 연도의 캠퍼스 모집 점수를 기반으로 순위를 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "요청 성공 - 모집 순위 데이터 반환",
                    content = @Content(schema = @Schema(implementation = CampusRecScoreResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/recruit/{year}")
    public Response<List<CampusRecScoreResponseDto>> getRecruitRanking(@PathVariable("year") int year) {
        return Response.success(campusService.getCampusRecRankingByYear(year));
    }

    @Operation(
            summary = "캠퍼스 점수 스케줄러 실행",
            description = "캠퍼스 연간 점수를 계산하고 저장하는 스케줄러를 실행합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @GetMapping("/test/scheduler")
    public Response<Void> demonstrationScheduler() {
        campusService.calculateAndSaveCampusYearlyScores();
        return Response.success();
    }

    @Operation(
            summary = "캠퍼스 목록 조회",
            description = "모든 캠퍼스의 목록을 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "요청 성공 - 캠퍼스 목록 반환",
                    content = @Content(schema = @Schema(implementation = CampusResponseDto.class))
            )
    })
    @GetMapping
    public Response<List<CampusResponseDto>> getCampusList() {
        return Response.success(campusService.getCampusList());
    }

    @Operation(
            summary = "특정 캠퍼스의 과정 조회",
            description = "특정 캠퍼스에 속한 과정을 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "요청 성공 - 과정 목록 반환",
                    content = @Content(schema = @Schema(implementation = CourseResponseDto.class))
            ),
            @ApiResponse(responseCode = "404", description = "캠퍼스를 찾을 수 없음")
    })
    @GetMapping("/{campusId}/courses")
    public Response<List<CourseResponseDto>> getCourseListInCampus(@PathVariable("campusId") Long campusId) {
        return Response.success(campusService.getCourseListInCampus(campusId));
    }
}
