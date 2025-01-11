package com.semoi.semo.applyForm.dto.responsedto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyFormListResponseDto {

    private Long applyFormId;
    private Long boardId;
    private String boardTitle;
    private String username;
    private String position;
    private String aboutMe;
    private String status;
    private LocalDateTime createdAt;

}
