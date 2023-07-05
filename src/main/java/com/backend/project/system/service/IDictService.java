package com.backend.project.system.service;

import com.backend.framework.web.domain.AjaxResult;
import com.backend.project.system.domain.DictLeague;
import com.backend.project.system.domain.DictTeam;

import java.util.List;

/**
 * Service接口
 *
 * @author
 */
public interface IDictService {

    public List<DictLeague> selectDictLeagueList();

    public List<DictTeam> selectDictTeamList();

    public AjaxResult insertDictLeague(DictLeague dictLeague);

    public AjaxResult insertDictTeam(DictTeam dictTeam);

}
