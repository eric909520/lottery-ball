package com.backend.project.system.mapper;

import com.backend.project.system.domain.LotteryData;
import com.backend.project.system.domain.NumberAnalyze;

import java.util.List;

/**
 * Mapper接口
 *
 * @author
 */
public interface NumberAnalyzeMapper {


    public int createByDate(String date);

    public int updateNumberAnalyze(NumberAnalyze numberAnalyze);

}
