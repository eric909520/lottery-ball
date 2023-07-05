package com.backend.project.system.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * 客队赔率 (胜负平)
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Had {
    /** 客胜赔率*/
    private String a;
    private String df;
    private Date updateDate;
    /** 平赔率 */
    private String d;
    private String af;
    /**客负赔率*/
    private String h;
    private String updateTime;
    private String hf;
    /**让球 */
    private String goalLine;

}
