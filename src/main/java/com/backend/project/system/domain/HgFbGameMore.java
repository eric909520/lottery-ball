package com.backend.project.system.domain;

import lombok.Data;

/**
 * hg football 水位
 * @author
 */
@Data
public class HgFbGameMore extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    // 联赛id - 关联HgFbLeagueData
    private String lid;

    // 比赛id - 关联HgFbLeagueData
    private String ecid;

    // 主加05
    private String hAdd05;

    // 主减05
    private String hCut05;

    // 客加05
    private String cAdd05;

    // 客减05
    private String cCut05;

    // 主胜
    private String myselfH;

    // 客胜
    private String myselfC;

    // 和局
    private String myselfN;

    // 总进球0-1
    private String total01;

    // 总进球2-3
    private String total23;

    // 总进球4-6
    private String total46;

    // 总进球7
    private String total7;

    // 大1.5
    private String big15;

    // 大2.5
    private String big25;

    // 大3.5
    private String big35;

    // 大1.5/2
    private String big15_2;

    // 大2/2.5
    private String big2_25;

    // 大2.5/3
    private String big25_3;

    // 大3/3.5
    private String big3_35;

    // 小2.5
    private String small25;

    // 小3.5
    private String small35;

    // 小2/2.5
    private String small2_25;

    // 小2.5/3
    private String small25_3;

    // 小3/3.5
    private String small3_35;

    // 小3.5/4
    private String small35_4;

}
