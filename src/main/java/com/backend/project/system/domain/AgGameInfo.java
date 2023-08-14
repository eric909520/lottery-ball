package com.backend.project.system.domain;

import lombok.Data;

/**
 * ag football 比赛信息
 * @author
 */
@Data
public class AgGameInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String matchId;

    private String myselfH;

    private String myselfN;

    private String myselfA;

    private String homeAdd;

    private String homeCut;

    private String awayAdd;

    private String awayCut;

    private String s0;

    private String s1;

    private String s2;

    private String s3;

    private String s4;

    private String s5;

    private String s6;

    public AgGameInfo() {}

    public AgGameInfo(String matchId, String myselfH, String myselfN, String myselfA, String homeAdd, String homeCut
            , String awayAdd, String awayCut, String s0, String s1, String s2, String s3, String s4, String s5, String s6) {
        this.matchId = matchId;
        this.myselfH = myselfH;
        this.myselfN = myselfN;
        this.myselfA = myselfA;
        this.homeAdd = homeAdd;
        this.homeCut = homeCut;
        this.awayAdd = awayAdd;
        this.awayCut = awayCut;
        this.s0 = s0;
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
        this.s5 = s5;
        this.s6 = s6;
    }

}
