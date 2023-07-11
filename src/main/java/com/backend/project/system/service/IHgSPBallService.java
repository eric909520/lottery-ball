package com.backend.project.system.service;

import com.backend.project.system.domain.vo.BetBasketballParamVo;
import com.backend.project.system.domain.vo.BetParamVo;

/**
 * Service接口
 *
 * @author
 */
public interface IHgSPBallService {

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

    /**
     * 体彩主队胜，皇冠客队加
     * @param betParamVo
     */
    BetParamVo SPWin_HGVisitAdd05(BetParamVo betParamVo);

    /**
     * 体彩主队负，皇冠主队加
     * @param betParamVo
     */
    BetParamVo SPLose_HGHomeAdd05(BetParamVo betParamVo);

    /**
     * 体彩主队让球负，皇冠主队减05
     * @param betParamVo
     */
    BetParamVo SPRangLose_HGHomeCut05(BetParamVo betParamVo);

    /**
     * 体彩主队受球胜(体彩主队+)，皇冠（客队减05胜
     * @param betParamVo
     */
    BetParamVo SPShouWin_HGVisitCut05(BetParamVo betParamVo);
}
