package com.backend.project.system.domain;

import lombok.Data;

/**
 *
 * @author
 */
@Data
public class AgApi extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String apiLink;

    private String api;

    private String token;

    private String uId;

    private Integer sportType;

    private Integer marketType;

    private Integer pageIndex;

    private String matchId;

}
