package com.backend.project.system.mapper;

import com.backend.framework.aspectj.lang.annotation.DataSource;
import com.backend.framework.aspectj.lang.enums.DataSourceType;
import com.backend.project.system.domain.AppConfig;
import com.backend.project.system.domain.LotteryData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper接口
 *
 * @author
 */
public interface LotteryDataMapper {

    public LotteryData selectLotteryDataById(Long id);

    public LotteryData selectLotteryDataByPreDrawIssue(int preDrawIssue);

    public List<LotteryData> selectLotteryDataList(LotteryData lotteryData);

    /**
     * 根据时间范围查询
     * @param startTime
     * @param endTime
     * @return
     */
    public List<LotteryData> selectLotteryDataListByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 根据期数范围查询
     * @param limit
     * @return
     */
    public List<LotteryData> selectListByPreDrawIssue(int limit);

    public int insertLotteryData(LotteryData lotteryData);

}
