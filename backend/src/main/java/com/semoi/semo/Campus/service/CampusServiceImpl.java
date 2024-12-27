package com.semoi.semo.Campus.service;

import com.semoi.semo.Campus.domain.Campus;
import com.semoi.semo.Campus.domain.CampusYearlyScore;
import com.semoi.semo.Campus.dto.CampusActScoreResponseDto;
import com.semoi.semo.Campus.dto.CampusRecScoreResponseDto;
import com.semoi.semo.Campus.dto.CampusTotalScoreAndNameDto;
import com.semoi.semo.Campus.enums.CampusName;
import com.semoi.semo.Campus.repository.CampusRepository;
import com.semoi.semo.Campus.repository.CampusYearlyScoreRepository;
import com.semoi.semo.global.exception.ErrorCode;
import com.semoi.semo.global.exception.SemoException;
import com.semoi.semo.user.domain.User;
import com.semoi.semo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CampusServiceImpl implements CampusService{

    private final CampusRepository campusRepository;
    private final CampusYearlyScoreRepository scoreRepository;

    public List<CampusRecScoreResponseDto> getCampusRecRankingByYear(int year) {
        return scoreRepository.findByYearOrderByRecScoreDesc(year)
                .stream()
                .map(CampusRecScoreResponseDto::of)
                .toList();
    }

    public List<CampusActScoreResponseDto> getCampusActRankingByYear(int year) {
        return scoreRepository.findByYearOrderByActScoreDesc(year)
                .stream()
                .map(CampusActScoreResponseDto::of)
                .toList();
    }

    @Override
    public Campus getCampusOrElseThrow(CampusName campusName) {
        return campusRepository.findByCampusName(campusName)
                .orElseThrow(() -> new SemoException(ErrorCode.CAMPUS_NOT_FOUND));
    }

    /**
     * 현재 년도 캠퍼스 별 점수를 집계하는 로직
     */
    @Override
    public void calculateAndSaveCampusYearlyScores() {

        int currentYear = LocalDate.now().getYear();

        List<CampusTotalScoreAndNameDto> totalScoresGroupedByCampus = campusRepository.findTotalScoresAndCampusIdGroupedByCampusName();

        for (CampusTotalScoreAndNameDto dto : totalScoresGroupedByCampus) {

            Campus campus = campusRepository.findById(dto.campusId())
                    .orElse(null);

            // 등록된 캠퍼스가 없다면 넘어감.
            if (campus == null) {
                continue;
            }

            CampusYearlyScore campusYearlyScore = scoreRepository.findByCampusAndYear(campus, currentYear)
                    .orElse(null);

            // 해당 년도의 캠퍼스 행이 없다면 생성
            if (campusYearlyScore == null) {
                campusYearlyScore = scoreRepository.save(CampusYearlyScore.create(campus, currentYear));
            }

            // 점수 update
            campusYearlyScore.update(Math.toIntExact(dto.recScore()), Math.toIntExact(dto.actScore()));

            scoreRepository.save(campusYearlyScore);
        }
    }
}
