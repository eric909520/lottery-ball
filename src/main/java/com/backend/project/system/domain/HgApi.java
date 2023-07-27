package com.backend.project.system.domain;

import lombok.Data;

/**
 *
 * @author
 */
@Data
public class HgApi extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String apiLink;

    private String p;

    private String uId;

    private String ts;

    /** 接口VO参数 */
    private String lid;

    private String ecid;

    private String gid;

    private String wType;

    private String choseTeam;

}
