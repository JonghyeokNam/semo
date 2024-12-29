package com.semoi.semo.applyForm.service;

import com.semoi.semo.applyForm.dto.requestdto.ApplyFormRequestDto;
import com.semoi.semo.applyForm.dto.responsedto.ApplyFormListResponseDto;
import com.semoi.semo.applyForm.dto.responsedto.ApplyFormResponseDto;
import com.semoi.semo.applyForm.entity.ApplyForm;
import com.semoi.semo.applyForm.entity.Position;
import com.semoi.semo.applyForm.repository.ApplyFormRepository;
import com.semoi.semo.applyForm.repository.PositionRepository;
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
    private final PositionRepository positionRepository;
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

    public ApplyForm createApplyForm(Long boardId, ApplyFormRequestDto requestDto, Long userId) {

        // 포지션 ID를 기반으로 Position 엔티티 조회
        Position position = positionRepository.findById(requestDto.getPositionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid position ID: " + requestDto.getPositionId()));

        // Board ID 유효성 확인
        boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID: " + boardId));

        // 새로운 ApplyForm 생성 및 저장
        ApplyForm applyForm = ApplyForm.builder()
                .boardId(boardId)
                .userId(userId)
                .position(position)
                .aboutMe(requestDto.getAboutMe())
                .status("대기")
                .createdAt(java.time.LocalDateTime.now())
                .build();

        return applyFormRepository.save(applyForm);
    }

    public ApplyFormResponseDto getUserApplyForm(Long applyFormId, Long userId) {
        ApplyForm applyForm = applyFormRepository.findByApplyFormIdAndUserId(applyFormId, userId);
        if (applyForm == null) {
            throw new IllegalArgumentException("ApplyForm not found for given ID and User");
        }

        String boardTitle = boardRepository.findById(applyForm.getBoardId())
                .map(Board::getTitle)
                .orElse("Unknown Board Title"); // 예외, 제목을 읽을 수 없거나 없을 때

        return ApplyFormResponseDto.builder()
                .applyFormId(applyForm.getApplyFormId())
                .boardId(applyForm.getBoardId())
                .boardTitle(boardTitle)
                .userId(applyForm.getUserId())
                .position(applyForm.getPosition().getName())
                .aboutMe(applyForm.getAboutMe())
                .status(applyForm.getStatus())
                .createdAt(applyForm.getCreatedAt())
                .build();
    }

    public void updateUserApplyForm(Long applyFormId, ApplyFormRequestDto requestDto, Long userId) {
        // 신청서 조회
        ApplyForm applyForm = applyFormRepository.findByApplyFormIdAndUserId(applyFormId, userId);
        if (applyForm == null) {
            throw new IllegalArgumentException("ApplyForm not found for given ID and User");
        }

        Position position = positionRepository.findById(requestDto.getPositionId())
                .orElseThrow(() -> new IllegalArgumentException("Position not found for given ID"));

        // 수정 가능한 필드 업데이트
        applyForm.setPosition(position);
        applyForm.setAboutMe(requestDto.getAboutMe());

        // 저장
        applyFormRepository.save(applyForm);
    }
}
