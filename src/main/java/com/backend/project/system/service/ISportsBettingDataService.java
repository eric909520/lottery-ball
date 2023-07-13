package com.backend.project.system.service;

import com.backend.framework.web.domain.AjaxResult;
import com.backend.project.system.domain.BetSPMatchInfo;

/**
 *  体彩数据获取
 */
public interface ISportsBettingDataService {

    /**
     *  获取体彩足球数据
     */
    void  getSportsBettingFBData();

    /**
     * 获取体彩篮球数据
     */
    void  getSportsBettingBKData();

    /**
     * 将投注中的记录复制到投注表 并把投注状态设置成投注中
     */
    void betStart(BetSPMatchInfo betSPMatchInfo);

    /**
     * 录入皇冠投注数据，计算体彩投注金额
     * @param betSPMatchInfo
     * @return
     */
    AjaxResult betInfoInput(BetSPMatchInfo betSPMatchInfo);


    /**
     *  清理当天已经过期的数据
     */
    void cleanObsoleteData();

    /**
     * 拆分投注额
     * @param betId
     * @return
     */
    AjaxResult splitAmount(Long betId);
}
