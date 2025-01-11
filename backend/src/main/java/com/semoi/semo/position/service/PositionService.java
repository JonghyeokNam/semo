package com.semoi.semo.position.service;

import com.semoi.semo.position.dto.responsedto.PositionResponseDto;
import com.semoi.semo.position.entity.Position;
import com.semoi.semo.position.repository.PositionRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 주현우
@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    public List<PositionResponseDto> getAllPositions() {
        // 데이터베이스에서 모든 포지션 조회
        List<Position> positions = positionRepository.findAll();

        // Entity -> DTO 변환
        return positions.stream().map(position ->
                PositionResponseDto.builder()
                        .positionId(position.getPositionId())
                        .name(position.getName())
                        .description(position.getDescription())
                        .build()
        ).collect(Collectors.toList());
    }
}