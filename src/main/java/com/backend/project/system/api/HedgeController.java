package com.backend.project.system.api;

import com.backend.framework.web.controller.BaseController;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.project.system.domain.vo.HedgeParamVo;
import com.backend.project.system.service.IHedgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 对冲 Controller
 *
 * @author
 * @date
 */
@RestController
@RequestMapping("/system/open/api/hedge/")
@Slf4j
public class HedgeController extends BaseController {

    @Resource
    private IHedgeService hedgeService;

    /**
     * 投注测试 - 欧盘赔率
     */
    @PostMapping(value = "/betCheck")
    public AjaxResult betCheck(@RequestBody HedgeParamVo hedgeParamVo) {
        try {
            hedgeService.betCheck(hedgeParamVo);

            return AjaxResult.success();
        } catch (Exception e) {
            log.info("API - betCheck exception ----->>>>", e);
            return AjaxResult.error();
        }
    }

}
