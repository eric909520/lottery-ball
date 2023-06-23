package com.backend.project.system.api;

import com.backend.framework.web.controller.BaseController;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.project.system.domain.vo.BetBasketballParamVo;
import com.backend.project.system.domain.vo.BetParamVo;
import com.backend.project.system.service.IBallService;
import com.backend.project.system.service.IBallTempService;
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
@RequestMapping("/system/open/api/ball/")
@Slf4j
public class BallOpenApiController extends BaseController {

    @Resource
    private IBallService ballService;

    @Resource
    private IBallTempService ballTempService;

    /**
     * 投注测试
     */
    @PostMapping(value = "/betCheck")
    public AjaxResult betCheck(@RequestBody BetParamVo betParamVo) {
        try {
            ballService.betCheck(betParamVo);

            return AjaxResult.success();
        } catch (Exception e) {
            log.info("API - betCheck exception ----->>>>", e);
            return AjaxResult.error();
        }
    }

    /**
     * 投注测试 - 012
     */
    @PostMapping(value = "/betCheckTemp")
    public AjaxResult betCheckTemp(@RequestBody BetParamVo betParamVo) {
        try {
            ballTempService.betCheck(betParamVo);

            return AjaxResult.success();
        } catch (Exception e) {
            log.info("API - betCheck exception ----->>>>", e);
            return AjaxResult.error();
        }
    }

    /**
     * 投注测试 - 单关胜平负
     */
    @PostMapping(value = "/betCheckSingle")
    public AjaxResult betCheckSingle(@RequestBody BetParamVo betParamVo) {
        try {
            ballTempService.betCheckSingle(betParamVo);

            return AjaxResult.success();
        } catch (Exception e) {
            log.info("API - betCheck exception ----->>>>", e);
            return AjaxResult.error();
        }
    }

    /**
     * 投注测试 - 单关胜平负
     */
    @PostMapping(value = "/betBasketball")
    public AjaxResult betBasketball(@RequestBody BetBasketballParamVo basketballParamVo) {
        try {
            ballTempService.betBasketball(basketballParamVo);

            return AjaxResult.success();
        } catch (Exception e) {
            log.info("API - betCheck exception ----->>>>", e);
            return AjaxResult.error();
        }
    }
}
