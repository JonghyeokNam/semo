package com.semoi.semo.schedule.config;

import com.semoi.semo.Campus.service.CampusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfig {

    private final CampusService campusService;

    /**
     * 매일 새벽 2시 캠퍼스 별 점수 집계 갱신
     */
    @Scheduled(cron = "0 0 2 * * *")
    private void calculateDailyScores() {
        campusService.calculateAndSaveCampusYearlyScores();
    }
}