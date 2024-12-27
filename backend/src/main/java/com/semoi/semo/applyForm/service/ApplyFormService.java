package com.semoi.semo.applyForm.service;

import com.semoi.semo.applyForm.dto.responsedto.ApplyFormListResponseDto;
import com.semoi.semo.applyForm.entity.ApplyForm;
import com.semoi.semo.applyForm.repository.ApplyFormRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyFormService {

    private final ApplyFormRepository applyFormRepository;

//    public List<ApplyFormListResponseDto> getApplyFormsByBoardId(Long boardId) {
//        List<ApplyForm> applyForms = applyFormRepository.findByBoardId(boardId);
//
//        return applyForms.stream().map(applyForm -> ApplyFormListResponseDto.builder()
//                .applyFormId(applyForm.getApplyFormId())
//                .boardId(applyForm.getBoardId())
//                .userId(applyForm.getUserId())
//                .position(applyForm.getPosition().getName())
//                .aboutMe(applyForm.getAboutMe())
//                .status(applyForm.getStatus())
//                .createdAt(applyForm.getCreatedAt())
//                .build()
//        ).collect(Collectors.toList());
//    }

    public List<ApplyFormListResponseDto> getApplyFormsByBoardId(Long boardId) {
        List<ApplyForm> applyForms = applyFormRepository.findByBoardId(boardId);
        System.out.println("Fetched apply forms: " + applyForms);

        return applyForms.stream().map(applyForm -> {
                return ApplyFormListResponseDto.builder()
                        .applyFormId(applyForm.getApplyFormId())
                        .boardId(applyForm.getBoardId())
                        .userId(applyForm.getUserId())
                        .position(applyForm.getPosition().getName()) // NullPointerException 가능성 체크
                        .aboutMe(applyForm.getAboutMe())
                        .status(applyForm.getStatus())
                        .createdAt(applyForm.getCreatedAt())
                        .build();
        }).collect(Collectors.toList());
    }

}
