package com.backend.project.system.mapper;

import com.backend.project.system.domain.SPBKMatchInfo;
import com.backend.project.system.domain.SPMatchInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体彩比赛信息Mapper
 */
public interface SPMatchInfoMapper {

    /**
     * 查询
     */
    SPMatchInfo selectCondition(@Param("leagueName") String leagueName, @Param("homeTeam") String homeTeam, @Param("awayTeam") String awayTeam);

    /**
     * 保存体彩足球比赛信息
     * @param list
     * @return
     */
    int insertSPMatchInfos(List<SPMatchInfo> list);
    int insertSPMatchInfo(SPMatchInfo spMatchInfo);

    /**
     * 删除旧足球数据
     * @return
     */
    int deleteMatchInfo();

    /**
     * 删除旧篮球数据
     */
    void deleteBKMatchInfo();

    /**
     * 插入篮球数据
     * @param list
     */
    void insertSPBKMatchInfos(List<SPBKMatchInfo> list);

    /**
     * 根据日期查询联赛列表
     * @param date
     * @return
     */
    List<String> getTodayLeague(String date);

    /**
     * 根据日期查询主队球队列表
     * @param date
     * @return
     */
    List<String> getTodayHomeTeam(String date);

    /**
     * 根据日期查询客队球队列表
     * @param date
     * @return
     */
    List<String> getTodayAwayTeam(String date);

    /**
     * 通过比赛编号和开始时间查询比赛
     * @param matchNum
     * @param matchDate
     * @return
     */
    SPMatchInfo findSPMatchInfo(Integer matchNum, String matchDate);

    int updateMatchInfo(List<SPMatchInfo> list);
}
