package com.backend.project.system.schedule;

import com.backend.framework.config.ThreadPoolConfig;
import com.backend.project.system.service.ISportsBettingDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

@Configuration
@EnableScheduling
@Slf4j
public class SPDataSchedule {
    @Resource
    private ThreadPoolConfig threadPoolConfig;

    @Resource
    private ISportsBettingDataService sportsBettingDataService;

    @Scheduled(cron = "0 0/1 * * * ? ")
    private void getSPMatchData() {
        threadPoolConfig.threadPoolExecutor().submit(() -> {
//            sportsBettingDataService.getSportsBettingFBData();
            sportsBettingDataService.getSportsBettingBKData();
        });
    }

}
