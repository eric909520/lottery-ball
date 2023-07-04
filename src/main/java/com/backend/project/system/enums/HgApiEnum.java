package com.backend.project.system.enums;

/**
 * 系统参数配置类型枚举类
 */
public enum HgApiEnum {

    get_league_list_All("get_league_list_All", "今日足球赛事"),
    get_game_list("get_game_list", "赛事下属球赛列表"),
    get_game_more("get_game_more", "球赛详细"),
    ;

    HgApiEnum(String api, String desc) {
        this.api = api;
        this.desc = desc;
    }

    private String api;

    private String desc;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
