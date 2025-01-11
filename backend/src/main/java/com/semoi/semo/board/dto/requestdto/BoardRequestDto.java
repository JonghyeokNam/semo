package com.semoi.semo.board.dto.requestdto;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
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
    private List<String> recruitmentTypes;

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
