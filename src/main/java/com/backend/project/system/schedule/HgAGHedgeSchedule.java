package com.backend.project.system.schedule;

import com.backend.framework.config.ThreadPoolConfig;
import com.backend.project.system.service.IHgScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

/**
 * hg - ag
 */
@Configuration
@EnableScheduling
@Slf4j
public class HgAGHedgeSchedule {

    @Resource
    private ThreadPoolConfig threadPoolConfig;
    @Resource
    private IHgScheduleService hgScheduleService;

    /**
     * hg - sp 数据计算 - 单关
     */
    //    @Scheduled(cron="0 0/1 * * * ?")
//    @Scheduled(fixedDelay = 120000L)
    private void hedge_Hg_SP_data_single() {
        threadPoolConfig.threadPoolExecutor().submit(() -> {
            hgScheduleService.hedge_Hg_Ag_data_single();
        });
    }

    /**
     * hg - sp 数据计算 - 012
     */
    //    @Scheduled(cron="0 0/1 * * * ?")
//    @Scheduled(fixedDelay = 130000L)
    private void hedge_Hg_SP_data_012() {
        threadPoolConfig.threadPoolExecutor().submit(() -> {
            hgScheduleService.hedge_Hg_Ag_data_012();
        });
    }

}
