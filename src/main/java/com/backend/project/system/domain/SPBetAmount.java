package com.backend.project.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 投注列表表(sp_bet_amount_list)
 */
@Data
public class SPBetAmount implements Serializable {
    private Long id;

    private Long betId;

    private List amount;
}
