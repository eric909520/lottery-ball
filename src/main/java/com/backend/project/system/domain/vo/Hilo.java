package com.backend.project.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 篮球大小分数据vo
 */
@Data
public class Hilo  implements Serializable {
    //大小分基数
    private String goalLine;
    //大于
    private String h;
    //小于
    private String l;
}
