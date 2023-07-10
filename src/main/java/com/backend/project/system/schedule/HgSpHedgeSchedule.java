package com.backend.project.system.schedule;

import com.backend.common.utils.http.HttpUtils;
import com.backend.framework.config.ThreadPoolConfig;
import com.backend.project.system.service.IHgScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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
     * hg - sp 数据计算
     */
    //    @Scheduled(cron="0 0/1 * * * ?")
    @Scheduled(fixedDelay = 2000000L)
    private void hedge_Hg_SP_data() {
        threadPoolConfig.threadPoolExecutor().submit(() -> {
            hgScheduleService.hedge_Hg_SP_data();
        });
    }

    public static void main(String[] args) {
        String tgUrl = "https://api.telegram.org/bot6347199448:AAFTxBhJL8ZrPjhxn7BL2Bj7-MdoKzySWPA/sendMessage";
        HttpUtils.sendPost(tgUrl, "chat_id=-906665985&text=⚽⚽球赛监测⚽⚽ 比赛编号:\n 123123 ");
    }

}
