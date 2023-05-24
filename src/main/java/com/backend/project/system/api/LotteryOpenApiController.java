package com.backend.project.system.api;

import com.backend.common.utils.CalcUtil;
import com.backend.common.utils.DateUtils;
import com.backend.framework.web.controller.BaseController;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.project.system.domain.LotteryData;
import com.backend.project.system.domain.NumberAnalyze;
import com.backend.project.system.mapper.LotteryDataMapper;
import com.backend.project.system.schedule.LotterySchedule;
import com.backend.project.system.service.ILotteryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * 开放接口 Controller
 *
 * @author
 * @date
 */
@RestController
@RequestMapping("/system/open/api/lottery/")
@Slf4j
public class LotteryOpenApiController extends BaseController {

    @Resource
    private ILotteryService lotteryService;

    /**
     * 获取号码 - 根据最近一小时数据推荐
     */
    @GetMapping(value = "/getNumber/hour/{hour}")
    public AjaxResult getNumberHour(@PathVariable("hour") int hour) {
        try {
            List<Map.Entry<String, Integer>> list = lotteryService.getNumbers(hour);
            return AjaxResult.success(list);
        } catch (Exception e) {
            log.info("API - getNumberHour exception ----->>>>", e);
            return AjaxResult.error();
        }
    }

}
