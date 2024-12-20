package com.semoi.semo.board.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class BoardResponseDto {

    private Long boardId;
    private String title;
    private String content;
    private Integer hit;
    private String recruitmentType;
    private Integer recruitmentCount;
    private String recruitmentField;
    private String recruitmentMethod;
    private LocalDateTime recruitmentDeadline;
    private String progressPeriod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AuthorDto author;

    @Data
    @Builder
    @AllArgsConstructor
    public static class AuthorDto {

        private Long userId;
        private String username;
    }
}

