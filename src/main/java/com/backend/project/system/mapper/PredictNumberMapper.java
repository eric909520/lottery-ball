package com.backend.project.system.mapper;

import com.backend.project.system.domain.NumberAnalyze;
import com.backend.project.system.domain.PredictNumber;

/**
 * Mapper接口
 *
 * @author
 */
public interface PredictNumberMapper {

    /**
     * 根据期数查询预测号码组
     * @param period
     * @return
     */
    public PredictNumber selectByPeriod(String period);

    /**
     * 写入记录
     * @param predictNumber
     * @return
     */
    public int insertPredictNumber(PredictNumber predictNumber);

    public int updatePredictNumber(PredictNumber predictNumber);

}
