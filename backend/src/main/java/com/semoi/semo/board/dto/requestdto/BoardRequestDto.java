package com.semoi.semo.board.dto.requestdto;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;

@NotNull
@Getter
@Setter
public class BoardRequestDto {

    private String title;
    private String content;
    private String recruitmentType;
    private Long recruitmentCount;
    private String recruitmentField;
    private String recruitmentMethod;
    private OffsetDateTime recruitmentDeadline;
    private String progressPeriod;
}
