package com.backend.project.system.service;

import com.backend.project.system.domain.vo.BetBasketballParamVo;
import com.backend.project.system.domain.vo.BetParamVo;

/**
 * Service接口 - zhuang
 *
 * @author
 */
public interface IHgSPDealerBallService {

    /**
     * 012
     * @param betParamVo
     */
    public void betCheck012(BetParamVo betParamVo);

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

    /**
     * 大15
     * 体彩小 01,皇冠 全输全赢
     * @param betParamVo
     */
    BetParamVo SP01_HG大15(BetParamVo betParamVo);

    /**
     * 大2.5
     * 体彩小 012,皇冠 全输全赢
     * @param betParamVo
     */
    BetParamVo SP012_HG大25(BetParamVo betParamVo);

    /**
     * 大3.5
     * 体彩小 0123,皇冠 大3.5 全输全赢
     * @param betParamVo
     */
    BetParamVo SP012_HG大35(BetParamVo betParamVo);

    /**
     * 大1.5/2
     * 体彩小 012,皇冠 大1.5/2, 2球体彩赢，皇冠赢一半
     * @param betParamVo
     */
    BetParamVo SP012_HG大15_2(BetParamVo betParamVo);

    /**
     * 大2/2.5
     * 体彩小 012,皇冠 大2/2.5, 2球皇冠输一半
     * @param betParamVo
     */
    BetParamVo SP012_HG大2_25(BetParamVo betParamVo);

    /**
     * 大3/3.5
     * 体彩小 0123,皇冠 大3/3.5 3球体彩赢，皇冠输一半
     * @param betParamVo
     */
    BetParamVo SP012_HG大3_35(BetParamVo betParamVo);

    /**
     * 大2.5/3
     * 体彩小 0123,皇冠 大2.5/3, 3球体彩赢，皇冠赢一半
     * @param betParamVo
     */
    BetParamVo SP012_HG大25_3(BetParamVo betParamVo);

    /**
     * 小2.5
     * 体彩大 34567+,皇冠 小2.5 全输全赢
     * @param betParamVo
     */
    BetParamVo SP34567_HG小25(BetParamVo betParamVo);

    /**
     * 小3.5
     * 体彩大 4567+,皇冠 小3.5 全输全赢
     * @param betParamVo
     */
    BetParamVo SP4567_HG小35(BetParamVo betParamVo);

    /**
     * 小2/2.5
     * 体彩大 234567+,皇冠 小2/2.5, 2球体彩赢，皇冠输一半
     * @param betParamVo
     */
    BetParamVo SP234567_HG小2_25(BetParamVo betParamVo);

    /**
     * 小2.5/3
     * 体彩大 34567+,皇冠 小2.5/3, 3球体彩赢，皇冠输一半
     * @param betParamVo
     */
    BetParamVo SP34567_HG小25_3(BetParamVo betParamVo);

    /**
     * 小3/3.5
     * 体彩大 34567+,皇冠 小3/3.5, 3球体彩赢，皇冠赢一半
     * @param betParamVo
     */
    BetParamVo SP34567_HG小3_35(BetParamVo betParamVo);

    /**
     * 小3.5/4
     * 体彩大 4567+,皇冠 小3.5/4, 4球体彩赢，皇冠输一半
     * @param betParamVo
     */
    BetParamVo SP4567_HG小35_4(BetParamVo betParamVo);


}
