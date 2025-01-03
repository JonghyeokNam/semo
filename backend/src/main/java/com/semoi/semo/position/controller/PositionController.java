package com.semoi.semo.position.controller;

import com.semoi.semo.position.dto.responsedto.PositionResponseDto;
import com.semoi.semo.position.service.PositionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @GetMapping
    public List<PositionResponseDto> getAllPositions() {
        return positionService.getAllPositions();
    }

}
