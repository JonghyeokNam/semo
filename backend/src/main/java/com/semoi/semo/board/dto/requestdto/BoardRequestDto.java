package com.semoi.semo.board.dto.requestdto;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDto {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String recruitmentType;

    @NotNull
    private Long recruitmentCount;

    @NotNull
    private String recruitmentField;

    @NotNull
    private String recruitmentMethod;

    @NotNull
    private OffsetDateTime recruitmentDeadline;

    @NotNull
    private String progressPeriod;

    private OffsetDateTime updatedAt;
}
