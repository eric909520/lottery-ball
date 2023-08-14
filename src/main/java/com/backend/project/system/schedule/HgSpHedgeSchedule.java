package com.backend.project.system.schedule;

import com.backend.common.utils.http.HttpUtils;
import com.backend.framework.config.ThreadPoolConfig;
import com.backend.project.system.service.IHgScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

/**
 * hg - sp
 */
@Configuration
@EnableScheduling
@Slf4j
public class HgSpHedgeSchedule {

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
            hgScheduleService.hedge_Hg_SP_data_single();
        });
    }

    /**
     * hg - sp 数据计算 - 012
     */
    //    @Scheduled(cron="0 0/1 * * * ?")
//    @Scheduled(fixedDelay = 130000L)
    private void hedge_Hg_SP_data_012() {
        threadPoolConfig.threadPoolExecutor().submit(() -> {
            hgScheduleService.hedge_Hg_SP_data_012();
        });
    }

    /**
     * hg - sp 数据计算 - 单关 - zhuang
     */
    //    @Scheduled(cron="0 0/1 * * * ?")
//    @Scheduled(fixedDelay = 120000L)
    private void hedge_Hg_SP_data_single_dealer() {
        threadPoolConfig.threadPoolExecutor().submit(() -> {
            hgScheduleService.hedge_Hg_SP_data_single_dealer();
        });
    }

    /**
     * hg - sp 数据计算 - 012 - zhuang
     */
    //    @Scheduled(cron="0 0/1 * * * ?")
//    @Scheduled(fixedDelay = 130000L)
    private void hedge_Hg_SP_data_012_dealer() {
        threadPoolConfig.threadPoolExecutor().submit(() -> {
            hgScheduleService.hedge_Hg_SP_data_012_dealer();
        });
    }

    public static void main(String[] args) {
        String tgUrl = "https://api.telegram.org/bot6347199448:AAFTxBhJL8ZrPjhxn7BL2Bj7-MdoKzySWPA/sendMessage";
        HttpUtils.sendPost(tgUrl, "chat_id=-905019287&text=⚽⚽球赛监测⚽⚽ 比赛编号: 123123 ");
    }

}
