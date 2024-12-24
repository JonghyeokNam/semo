package com.semoi.semo.notification.controller;

import com.semoi.semo.global.response.Response;
import com.semoi.semo.notification.dto.NotificationResponseDto;
import com.semoi.semo.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(
            summary = "알림 목록 조회",
            description = "사용자의 모든 알림 목록을 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림 목록 조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public Response<List<NotificationResponseDto>> getNotifications(Authentication authentication) {
        return Response.success(notificationService.getNotifications(authentication.getName()));
    }

    @Operation(
            summary = "읽지 않은 알림 확인",
            description = "사용자에게 읽지 않은 알림이 있는지 확인합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "읽지 않은 알림 확인 성공",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "boolean"))),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(mediaType = "application/json")),
    })
    @GetMapping("/check")
    public Response<Boolean> checkNotifications(Authentication authentication) {
        return Response.success(notificationService.checkNotification(authentication.getName()));
    }

    @Operation(
            summary = "알림 읽음 처리",
            description = "특정 알림을 읽음 상태로 업데이트합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림 읽음 처리 성공",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "인증 실패",
                    content = @Content(mediaType = "application/json")),
    })
    @PutMapping("/{notificationId}")
    public Response<Void> readNotification(@PathVariable Long notificationId) {
        notificationService.readNotification(notificationId);
        return Response.success();
    }
}
