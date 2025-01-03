package com.semoi.semo.applyForm.dto.responsedto;

import com.semoi.semo.board.dto.responsedto.BoardResponseDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserApplyFormListResponseDto {

    private Long applyFormId;
    private BoardResponseDto board;
    private Long userId;
    private String position;
    private String aboutMe;
    private String status;
    private LocalDateTime createdAt;

}
