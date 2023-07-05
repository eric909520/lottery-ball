package com.backend.project.system.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * 客队赔率 (让球/受让球 赔率)
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Hhad {
    /** 负 */
    private String a;
    private String df;
    private Date updateDate;
    /** 平*/
    private String d;
    private String af;
    /**胜*/
    private String h;
    private String updateTime;
    private String hf;
    /**主队让球数*/
    private String goalLine;

}
