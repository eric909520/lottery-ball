package com.backend.project.system.mapper;

import com.backend.project.system.domain.SPMatchInfo;

import java.util.List;

/**
 * 体彩比赛信息Mapper
 */
public interface SPMatchInfoMapper {
    /**
     * 保存体彩足球比赛信息
     * @param spMatchInfo
     * @return
     */
    int insertSPMatchInfo(List<SPMatchInfo> spMatchInfo);

    int deleteMatchInfo();

}
