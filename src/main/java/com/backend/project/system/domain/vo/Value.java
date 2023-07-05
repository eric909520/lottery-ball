package com.backend.project.system.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class Value {

    /**
     * 足球数据列表
     */
    private List<MatchInfoList> matchInfoList;


    private AllUpList allUpList;
    /**数据总条数*/
    private int totalCount;

    private String lastUpdateTime;
}
