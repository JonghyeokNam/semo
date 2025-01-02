package com.semoi.semo.board.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.semoi.semo.applyForm.repository.ApplyFormRepository;
import com.semoi.semo.board.entity.Board;
import com.semoi.semo.comment.repository.CommentRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값 제외
public class BoardResponseDto {

    private Long boardId;
    private String title;
    private String content;
    private Integer hit;
    private List<String> recruitmentTypes;
    private Integer recruitmentCount;
    private String recruitmentField;
    private String recruitmentMethod;
    private LocalDateTime recruitmentDeadline;
    private String progressPeriod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer commentCount; // 댓글 수
    @JsonProperty("isParticipated")
    private boolean isParticipated; // 참가 여부
    @JsonProperty("isClosed")
    private boolean isClosed; // 모집 종료 여부
    private AuthorDto author;
    private ApplyFormsDto applyForms; // 신청자 정보

    @Data
    @Builder
    @AllArgsConstructor
    public static class AuthorDto {

        private String username;
        @JsonProperty("isAuthor") // JSON 직렬화 시 필드 이름 보장
        private boolean isAuthor; // 작성자 여부 추가
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class ApplyFormsDto {
        private int backend; // 백엔드 지원자 수
        private int frontend; // 프론트엔드 지원자 수
        private int marketer; // 마케터 지원자 수
        private int designer; // 디자이너 지원자 수
    }

    // Service 간소화 함수
    public static BoardResponseDto fromEntity(Board board, String userEmail,
            Boolean isParticipated,
            ApplyFormRepository applyFormRepository,
            CommentRepository commentRepository) {

        // 작성자 여부
        boolean isAuthor = board.getUser().getLoginEmail().equals(userEmail);

        // 모집 종료 여부
        boolean isClosed = board.getRecruitmentDeadline().isBefore(LocalDateTime.now());

        // 댓글 수 조회
        int commentCount = commentRepository.countByBoardId(board.getBoardId());

        // 분야별 신청자 수 조회
        ApplyFormsDto applyFormsDto = ApplyFormsDto.builder()
                .backend(applyFormRepository.countByBoardIdAndPositionName(board.getBoardId(), "backend"))
                .frontend(applyFormRepository.countByBoardIdAndPositionName(board.getBoardId(), "frontend"))
                .marketer(applyFormRepository.countByBoardIdAndPositionName(board.getBoardId(), "marketer"))
                .designer(applyFormRepository.countByBoardIdAndPositionName(board.getBoardId(), "designer"))
                .build();

        // DTO 생성
        return BoardResponseDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .hit(board.getHit())
                .recruitmentTypes(board.getRecruitmentTypes())
                .recruitmentCount(board.getRecruitmentCount())
                .recruitmentField(board.getRecruitmentField())
                .recruitmentMethod(board.getRecruitmentMethod())
                .recruitmentDeadline(board.getRecruitmentDeadline())
                .progressPeriod(board.getProgressPeriod())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .commentCount(commentCount)
                .isParticipated(isParticipated)
                .isClosed(isClosed)
                .author(AuthorDto.builder()
                        .username(board.getUser().getUsername())
                        .isAuthor(isAuthor)
                        .build())
                .applyForms(applyFormsDto)
                .build();
    }
}