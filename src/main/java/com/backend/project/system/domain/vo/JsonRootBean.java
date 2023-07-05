package com.backend.project.system.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class JsonRootBean {

    private Date businessDate;
    private List<SubMatchList> subMatchList;
    private String weekday;
    private int matchCount;

}