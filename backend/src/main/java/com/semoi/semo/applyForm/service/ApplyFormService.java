package com.semoi.semo.applyForm.service;

import com.semoi.semo.applyForm.dto.requestdto.ApplyFormRequestDto;
import com.semoi.semo.applyForm.dto.requestdto.ApplyFormStatusRequestDto;
import com.semoi.semo.applyForm.dto.responsedto.ApplyFormListResponseDto;
import com.semoi.semo.applyForm.dto.responsedto.ApplyFormResponseDto;
import com.semoi.semo.applyForm.dto.responsedto.UserApplyFormListResponseDto;
import com.semoi.semo.applyForm.entity.ApplyForm;
import com.semoi.semo.global.exception.ErrorCode;
import com.semoi.semo.global.exception.SemoException;
import com.semoi.semo.notification.enums.Type;
import com.semoi.semo.notification.service.NotificationService;
import com.semoi.semo.position.entity.Position;
import com.semoi.semo.applyForm.repository.ApplyFormRepository;
import com.semoi.semo.board.dto.responsedto.BoardResponseDto;
import com.semoi.semo.board.entity.Board;
import com.semoi.semo.board.repository.BoardRepository;
import com.semoi.semo.comment.repository.CommentRepository;
import com.semoi.semo.global.exception.DataNotFoundException;
import com.semoi.semo.jwt.service.TokenProvider;
import com.semoi.semo.position.entity.Position;
import com.semoi.semo.position.repository.PositionRepository;
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
    private final CommentRepository commentRepository;
    private final TokenProvider tokenProvider;
    private final NotificationService notificationService;

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
            User user = userRepository.findById(applyForm.getUserId())
                    .orElseThrow(()-> new DataNotFoundException("User not found"));

                return ApplyFormListResponseDto.builder()
                        .applyFormId(applyForm.getApplyFormId())
                        .boardId(applyForm.getBoardId())
                        .username(user.getUsername())
                        .position(applyForm.getPosition().getName())
                        .aboutMe(applyForm.getAboutMe())
                        .status(applyForm.getStatus())
                        .createdAt(applyForm.getCreatedAt())
                        .build();
        }).collect(Collectors.toList());
    }

    public List<UserApplyFormListResponseDto> getUserApplyForms(HttpServletRequest request) {
        // 사용자 이메일 추출
        String userEmail = tokenProvider.getUserLoginEmail(request);

        // 사용자 조회
        User user = userRepository.findByLoginEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        List<ApplyForm> applyForms = applyFormRepository.findByUserId(user.getUserId());

        return applyForms.stream().map(applyForm -> {
            // 게시글 정보 조회
            Board board = boardRepository.findById(applyForm.getBoardId())
                    .orElseThrow(() -> new DataNotFoundException("Board not found"));

            BoardResponseDto boardResponseDto = BoardResponseDto.fromEntity(board, userEmail, false, applyFormRepository, commentRepository);

            return UserApplyFormListResponseDto.builder()
                    .applyFormId(applyForm.getApplyFormId())
                    .aboutMe(applyForm.getAboutMe())
                    .board(boardResponseDto)
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
        Position position = positionRepository.findByName(requestDto.getPositionName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid position ID: " + requestDto.getPositionName()));

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

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new SemoException(ErrorCode.BOARD_NOT_FOUND));

        notificationService.createNotification(Type.COMMENT_ALERT, board.getUser(), board);
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

        Position position = positionRepository.findByName(requestDto.getPositionName())
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

    public void updateApplyFormStatus(Long applyFormId, ApplyFormStatusRequestDto requestDto, HttpServletRequest request) {

        // 사용자 이메일 추출
        String userEmail = tokenProvider.getUserLoginEmail(request);

        // 사용자 조회
        User user = userRepository.findByLoginEmail(userEmail)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // 신청서 조회
        ApplyForm applyForm = applyFormRepository.findById(applyFormId)
                .orElseThrow(() -> new DataNotFoundException("ApplyForm not found"));

        Board board = boardRepository.findById(applyForm.getBoardId())
                .orElseThrow(() -> new DataNotFoundException("Board not found"));

        Long boardAuthorId = board.getUser().getUserId();
        Long userId = user.getUserId();

        if (!boardAuthorId.equals(userId)) {
            throw new DataNotFoundException("Only the board owner can update the status of an apply form.");
        }

        // 상태 업데이트
        applyForm.setStatus(requestDto.getStatus());

        // 변경사항 저장
        applyFormRepository.save(applyForm);

        // 알림 수신자
        User notificationUser = userRepository.findById(applyForm.getUserId())
                        .orElseThrow(() -> new SemoException(ErrorCode.USER_NOT_FOUND));
        
        // 알림 생성
        notificationService.createNotification(Type.PARTICIPATION_RESULT, notificationUser, board);
    }

}
