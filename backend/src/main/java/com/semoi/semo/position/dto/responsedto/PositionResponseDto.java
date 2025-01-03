package com.semoi.semo.position.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PositionResponseDto {
    private Long positionId;      // 포지션 ID
    private String name;         // 포지션 이름
    private String description;  // 포지션 설명
}