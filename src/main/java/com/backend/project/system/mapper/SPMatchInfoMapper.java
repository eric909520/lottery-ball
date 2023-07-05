package com.backend.project.system.mapper;

import com.backend.project.system.domain.SPBKMatchInfo;
import com.backend.project.system.domain.SPMatchInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体彩比赛信息Mapper
 */
public interface SPMatchInfoMapper {
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
}
