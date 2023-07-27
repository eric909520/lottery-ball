package com.backend.project.system.enums;

/**
 *
 */
public enum AgApiEnum {

    get_match_list("get_match_list", "比赛列表"),
    get_match_info("get_match_info", "比赛信息"),
    ;

    AgApiEnum(String api, String desc) {
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
