package com.semoi.semo.applyForm.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyFormListResponseDto {

    private Long applyFormId;
    private Long boardId;
    private Long userId;
    private String position;
    private String contact;
    private String aboutMe;
    private String status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
