package com.backend.project.system.domain;

import lombok.Data;

/**
 * dict_league
 *
 * @author
 */
@Data
public class DictTeam extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    private String sp;

    private String hg;

}
