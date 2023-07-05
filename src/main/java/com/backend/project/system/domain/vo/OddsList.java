package com.backend.project.system.domain.vo;

/**
 * Copyright 2023 json.cn
 */
import lombok.Data;

import java.util.Date;

/**
 * 主队赔率
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class OddsList {
    /**主负赔率*/
    private String a;
    private Date updateDate;
//    private int matchNum;
    /**平*/
    private String d;
    private String odds;
    /**主胜*/
    private String h;
//    private long poolId;
    /**
     * 值为:HAD 是胜平负赔率
     * 值为:HHAD 是 让球/受让球 胜平负赔率
     * */
    private String poolCode;
    private String updateTime;
    private String goalLineF;
    private int matchId;
    private String goalLine;

}
