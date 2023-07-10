package com.backend.project.system.schedule;

import com.backend.framework.config.ThreadPoolConfig;
import com.backend.project.system.service.ISportsBettingDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

@Configuration
@EnableScheduling
@Slf4j
public class SPDataSchedule {
    @Resource
    private ThreadPoolConfig threadPoolConfig;

    @Resource
    private ISportsBettingDataService sportsBettingDataService;

//    @Scheduled(cron = "0 0/2 * * * ? ")
//    @Scheduled(fixedDelay = 600000L)
    private void getSPMatchDataFB() {
        threadPoolConfig.threadPoolExecutor().submit(() -> {
            sportsBettingDataService.getSportsBettingFBData();
        });
    }

//    @Scheduled(cron = "0 0/2 * * * ? ")
    private void getSPMatchDataBB() {
        threadPoolConfig.threadPoolExecutor().submit(() -> {
            sportsBettingDataService.getSportsBettingBKData();
        });
    }


    //    @Scheduled(cron = "0 0/2 * * * ? ")
    private void cleanObsoleteData(){
        threadPoolConfig.threadPoolExecutor().submit(() -> {
            sportsBettingDataService.cleanObsoleteData();
        });
    }

}
