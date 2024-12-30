package com.semoi.semo.applyForm.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyFormRequestDto {

    private Long positionId; // 참여 포지션
    private String aboutMe; // 포트폴리오 URL 등을 포함한 자기소개

}