package com.backend.project.system.api;

import com.backend.framework.web.controller.BaseController;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.project.system.domain.vo.BetHGVo;
import com.backend.project.system.domain.vo.BetParamVo;
import com.backend.project.system.service.IBallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

}
