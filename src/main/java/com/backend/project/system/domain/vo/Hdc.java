package com.backend.project.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 篮球让分数据vo
 */
@Data
public class Hdc implements Serializable {
    //主负
    private String a;
    //让分/被让分
    private String goalLine;
    //主胜
    private String h;


}
