package com.semoi.semo.campus.service;

import com.semoi.semo.campus.domain.Campus;
import com.semoi.semo.campus.domain.CampusYearlyScore;
import com.semoi.semo.campus.dto.response.CampusActScoreResponseDto;
import com.semoi.semo.campus.dto.response.CampusRecScoreResponseDto;
import com.semoi.semo.campus.repository.CampusRepository;
import com.semoi.semo.campus.repository.CampusYearlyScoreRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
class CampusServiceImplTest {

    @Autowired
    private CampusYearlyScoreRepository yearlyScoreRepository;
    @Autowired
    private CampusServiceImpl campusService;
    @Autowired
    private CampusRepository campusRepository;

    public void setUpCampusScore() {
        Campus firstCampus = campusRepository.findAll().getFirst();
        CampusYearlyScore FirstCampusYearlyScore = CampusYearlyScore.create(firstCampus, LocalDate.now().getYear());
        FirstCampusYearlyScore.update(10, 20);
        yearlyScoreRepository.save(FirstCampusYearlyScore);

        Campus LastCampus = campusRepository.findAll().getLast();
        CampusYearlyScore LastCampusyearlyScore = CampusYearlyScore.create(LastCampus, LocalDate.now().getYear());
        LastCampusyearlyScore.update(25, 15);
        yearlyScoreRepository.save(LastCampusyearlyScore);
    }

    @Test
    void getCampusRecRankingByYear() {
        // when
        List<CampusRecScoreResponseDto> campusRecRankingByYear = campusService.getCampusRecRankingByYear(LocalDate.now().getYear());

        // then
        assertThat(campusRecRankingByYear).isNotEmpty();
        assertThat(campusRecRankingByYear.size()).isEqualTo(2);
        assertThat(campusRecRankingByYear.getFirst().score()).isEqualTo(25);
    }

    @Test
    void getCampusActRankingByYear() {
        List<CampusActScoreResponseDto> campusActRankingByYear = campusService.getCampusActRankingByYear(LocalDate.now().getYear());

        assertThat(campusActRankingByYear).isNotEmpty();
        assertThat(campusActRankingByYear.size()).isEqualTo(2);
        assertThat(campusActRankingByYear.getFirst().score()).isEqualTo(20);
    }
}