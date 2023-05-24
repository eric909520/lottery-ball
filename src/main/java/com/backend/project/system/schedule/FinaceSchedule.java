package com.backend.project.system.schedule;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 财务相关计划类
 */
@Configuration
@EnableScheduling
@Slf4j
public class FinaceSchedule {

    ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5,
            new BasicThreadFactory.Builder().namingPattern("finance-schedule-pool-%d").daemon(true).build());

    /**
     * usdt实时汇率更新定时任务
     */
//    @Scheduled(fixedDelay = 1000L)
    private void exchangeRatesModified() {
        executorService.execute(() -> {
            log.info("===== usdt实时汇率更新定时任务启动 =====");
        });
    }

}
