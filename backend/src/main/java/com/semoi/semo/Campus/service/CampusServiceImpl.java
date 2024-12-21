package com.semoi.semo.Campus.service;

import com.semoi.semo.Campus.domain.Campus;
import com.semoi.semo.Campus.enums.CampusName;
import com.semoi.semo.Campus.repository.CampusRepository;
import com.semoi.semo.global.exception.ErrorCode;
import com.semoi.semo.global.exception.SemoException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CampusServiceImpl implements CampusService{

    private final CampusRepository campusRepository;

    @Override
    public Campus getCampusOrElseThrow(CampusName campusName) {
        return campusRepository.findByCampusName(campusName)
                .orElseThrow(() -> new SemoException(ErrorCode.CAMPUS_NOT_FOUND));
    }
}
