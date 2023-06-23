package com.backend.project.system.service;

import com.backend.project.system.domain.vo.BetBasketballParamVo;
import com.backend.project.system.domain.vo.BetParamVo;

/**
 * Service接口
 *
 * @author
 */
public interface IBallTempService {

    /**
     * 012
     * @param betParamVo
     */
    public void betCheck(BetParamVo betParamVo);

    /**
     * 单关
     * @param betParamVo
     */
    public void betCheckSingle(BetParamVo betParamVo);

    /**
     * 篮球
     * @param basketballParamVo
     */
    void betBasketball(BetBasketballParamVo basketballParamVo);
}
