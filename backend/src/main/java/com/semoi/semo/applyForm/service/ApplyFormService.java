package com.semoi.semo.applyForm.service;

import com.semoi.semo.applyForm.dto.responsedto.ApplyFormListResponseDto;
import com.semoi.semo.applyForm.entity.ApplyForm;
import com.semoi.semo.applyForm.repository.ApplyFormRepository;
import com.semoi.semo.board.entity.Board;
import com.semoi.semo.board.repository.BoardRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyFormService {

    private final ApplyFormRepository applyFormRepository;
    private final BoardRepository boardRepository;

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

    // 로그인 연결 필요!!
    // 주소에서 user id를 받는 것이 아닌 authentication id를 받도록 해야함.
    public List<ApplyFormListResponseDto> getUserApplyForms(Long userId) {

        List<ApplyForm> applyForms = applyFormRepository.findByUserId(userId);

        return applyForms.stream().map(applyForm -> {
            String boardTitle = boardRepository.findById(applyForm.getBoardId())
                    .map(Board::getTitle)
                    .orElse("Unknown Board Title"); // 예외, 제목을 읽을 수 없거나 없을 때

            return ApplyFormListResponseDto.builder()
                    .applyFormId(applyForm.getApplyFormId())
                    .boardId(applyForm.getBoardId())
                    .boardTitle(boardTitle)
                    .position(applyForm.getPosition().getName())
                    .status(applyForm.getStatus())
                    .createdAt(applyForm.getCreatedAt())
                    .build();
        }).collect(Collectors.toList());
    }
}
