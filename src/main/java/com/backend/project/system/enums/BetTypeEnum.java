package com.backend.project.system.enums;

/**
 * 投注组合类型
 */
public enum BetTypeEnum {

    win("win","体彩赔率-胜"),
    draw("draw","体彩赔率-平"),
    lose("lose","体彩赔率-负"),
    handicapWin("handicapWin","体彩赔率-主让/受让胜"),
    handicapLose("handicapLose","体彩赔率-主让/受负"),
    hedge_SPHomeWin_HGVisitAdd05("spHomeWin_hgVisitAdd05","赔率组合-体彩主胜，皇冠客+05"),
    hedge_SPVisitWin_HGHomeAdd05("spVisitWin_hgHomeAdd05","赔率组合-体彩客胜，皇冠主+05"),
    hedge_SPRangLose_HGHomeCut5("spRangLose_hgHomeCut05","赔率组合-体彩让球客胜，皇冠主-05"),
    hedge_SPShouWin_HGVisitCut05("spShouWin_hgVisitCut05","赔率组合-体彩主受球胜，皇冠客-05"),
    hedge_SPHomeWin_HGVisitWinAndTie("spHomeWin_hgVisitWinAndTie","赔率组合-体彩主胜，皇冠客胜&平"),
    hedge_SPTie_HGHomeWinAndVisitWin("spTie_hgHomeWinAndVisitWin","赔率组合-体彩平，皇冠主胜&客胜"),
    hedge_SPVisitWin_HGHomeWinAndTie("spVisitWin_hgHomeWinAndTie","赔率组合-体彩客胜，皇冠主胜&平"),

    big_15("big_15","总进球-大1.5"),
    big_15_2("big_15_2","总进球-大1.5/2"),
    big_2("big_2","总进球-大2"),
    big_25("big_2_5","总进球-大2.5"),
    big_2_25("big_2_25","总进球-大2/2.5"),
    big_25_3("big_25_3","总进球-大2.5/3"),
    big_35("big_35","总进球-大3.5"),
    big_3_35("big_3_35","总进球-大3/3.5"),

    small_25("small_25","总进球-小2.5"),
    small_2_25("small_2_25","总进球-小2/2.5"),
    small_25_3("small_25_3","总进球-小2.5/3"),
    small_35("small_35","总进球-小3.5"),
    small_3_35("small_3_35","总进球-小3/3.5"),
    small_35_4("small_35_4","总进球-小3.5/4"),

    total_ball_zero("total_ball_zero","总进球 0球赔率更新"),
    total_ball_one("total_ball_one","总进球 1球赔率更新"),
    total_ball_two("total_ball_two","总进球 2球赔率更新"),
    total_ball_three("total_ball_three","总进球 3球赔率更新"),
    total_ball_four("total_ball_four","总进球 4球赔率更新"),
    total_ball_five("total_ball_five","总进球 5球赔率更新"),
    total_ball_six("total_ball_six","总进球 6球赔率更新"),
    total_ball_seven("total_ball_seven","总进球 7球赔率更新"),



    ;

    private String value;

    private String desc;

    BetTypeEnum(String value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

}
