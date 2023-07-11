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
