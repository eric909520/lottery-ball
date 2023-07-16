package com.backend.project.system.api;

import com.backend.framework.web.controller.BaseController;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.project.system.domain.BetSPMatchInfo;
import com.backend.project.system.service.ISportsBettingDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 开放接口 Controller
 *
 * @author
 * @date
 */
@RestController
@RequestMapping("/system/open/api/ball/dealer/")
@Slf4j
public class HgDealerOpenApiController extends BaseController {

    @Resource
    private ISportsBettingDataService sportsBettingDataService;

    /**
     * 录入皇冠投注数据，计算体彩投注金额
     */
    @PostMapping(value = "/betInfoInput")
    public AjaxResult betInfoInput(@RequestBody BetSPMatchInfo betSPMatchInfo) {
        try {
            AjaxResult ajaxResult = sportsBettingDataService.betInfoInput(betSPMatchInfo);
            return ajaxResult;
        } catch (Exception e) {
            log.info("API - betInfoInput exception ----->>>>", e);
            return AjaxResult.error();
        }
    }

}
