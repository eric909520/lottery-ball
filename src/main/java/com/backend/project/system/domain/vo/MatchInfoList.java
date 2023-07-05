package com.backend.project.system.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class MatchInfoList {
    private String businessDate;
    /**详细数据列表*/
    private List<SubMatchList> subMatchList;

    private int matchCount;
}
