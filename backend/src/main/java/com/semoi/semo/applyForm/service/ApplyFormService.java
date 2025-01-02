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
import com.semoi.semo.global.exception.DataNotFoundException;
import com.semoi.semo.jwt.service.TokenProvider;
import com.semoi.semo.user.domain.User;
import com.semoi.semo.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
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
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public List<ApplyFormListResponseDto> getApplyFormsByBoardId(Long boardId, HttpServletRequest request) {
        // 사용자 이메일 추출
        String userEmail = tokenProvider.getUserLoginEmail(request);

        // 게시글 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new DataNotFoundException("Board not found"));

        // 작성자 여부 확인
        if (!board.getUser().getLoginEmail().equals(userEmail)) {
            throw new DataNotFoundException("You are not the author of this board");
        }

        List<ApplyForm> applyForms = applyFormRepository.findByBoardId(boardId);
        System.out.println("Fetched apply forms: " + applyForms);

        return applyForms.stream().map(applyForm -> {
                return ApplyFormListResponseDto.builder()
                        .applyFormId(applyForm.getApplyFormId())
                        .boardId(applyForm.getBoardId())
                        .userId(applyForm.getUserId())
                        .position(applyForm.getPosition().getName())
                        .aboutMe(applyForm.getAboutMe())
                        .status(applyForm.getStatus())
                        .createdAt(applyForm.getCreatedAt())
                        .build();
        }).collect(Collectors.toList());
    }

    public List<ApplyFormListResponseDto> getUserApplyForms(HttpServletRequest request) {
        // 사용자 이메일 추출
        String userEmail = tokenProvider.getUserLoginEmail(request);

        // 사용자 조회
        User user = userRepository.findByLoginEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        List<ApplyForm> applyForms = applyFormRepository.findByUserId(user.getUserId());

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

    public void createApplyForm(Long boardId, ApplyFormRequestDto requestDto, HttpServletRequest request) {

        // 사용자 이메일 추출
        String userEmail = tokenProvider.getUserLoginEmail(request);

        // 사용자 조회
        User user = userRepository.findByLoginEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // 포지션 ID를 기반으로 Position 엔티티 조회
        Position position = positionRepository.findById(requestDto.getPositionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid position ID: " + requestDto.getPositionId()));

        // Board ID 유효성 확인
        boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID: " + boardId));

        // 새로운 ApplyForm 생성 및 저장
        ApplyForm applyForm = ApplyForm.builder()
                .boardId(boardId)
                .userId(user.getUserId())
                .position(position)
                .aboutMe(requestDto.getAboutMe())
                .status("대기")
                .createdAt(java.time.LocalDateTime.now())
                .build();

        applyFormRepository.save(applyForm);
    }

    public ApplyFormResponseDto getUserApplyForm(Long applyFormId, HttpServletRequest request) {

        // 사용자 이메일 추출
        String userEmail = tokenProvider.getUserLoginEmail(request);

        // 사용자 조회
        User user = userRepository.findByLoginEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        ApplyForm applyForm = applyFormRepository.findByApplyFormIdAndUserId(applyFormId, user.getUserId());
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

    public void updateUserApplyForm(Long applyFormId, ApplyFormRequestDto requestDto, HttpServletRequest request) {

        // 사용자 이메일 추출
        String userEmail = tokenProvider.getUserLoginEmail(request);

        // 사용자 조회
        User user = userRepository.findByLoginEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // 신청서 조회
        ApplyForm applyForm = applyFormRepository.findByApplyFormIdAndUserId(applyFormId, user.getUserId());
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

    public void deleteApplyForm(Long applyFormId, HttpServletRequest request) {

        // 사용자 이메일 추출
        String userEmail = tokenProvider.getUserLoginEmail(request);

        // 사용자 조회
        User user = userRepository.findByLoginEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // 신청서 조회
        ApplyForm applyForm = applyFormRepository.findByApplyFormIdAndUserId(applyFormId, user.getUserId());
        if (applyForm == null) {
            throw new IllegalArgumentException("ApplyForm not found or you do not have permission to delete it");
        }

        // 삭제
        applyFormRepository.delete(applyForm);
    }
}
