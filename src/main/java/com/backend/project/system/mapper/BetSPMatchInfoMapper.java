package com.backend.project.system.mapper;

import com.backend.project.system.domain.BetSPMatchInfo;
import com.backend.project.system.domain.SPBKMatchInfo;
import com.backend.project.system.domain.SPMatchInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 正在投注的比赛信息
 */
public interface BetSPMatchInfoMapper {

    /**
     * 查询当前有没有正在投注中的足球⚽️比赛
     * @return
     */
    List<BetSPMatchInfo> findBettingRecord();
    /**
     * 将投注中的记录复制到投注表 并把投注状态设置成投注中
     */
    void insertBetSPMatchInfo(SPMatchInfo spMatchInfo);

    /**
     * 更新最后通知时间
     * @param id
     */
    void updateNotifyTime(Long id,Long timeStamp);


}
