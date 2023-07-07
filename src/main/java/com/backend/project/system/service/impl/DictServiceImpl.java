package com.backend.project.system.service.impl;

import com.backend.framework.web.domain.AjaxResult;
import com.backend.project.system.domain.DictLeague;
import com.backend.project.system.domain.DictTeam;
import com.backend.project.system.mapper.DictLeagueMapper;
import com.backend.project.system.mapper.DictTeamMapper;
import com.backend.project.system.service.IDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author
 */
@Service
public class DictServiceImpl implements IDictService {

    @Resource
    private DictLeagueMapper dictLeagueMapper;

    @Resource
    private DictTeamMapper dictTeamMapper;


    @Override
    public List<DictLeague> selectDictLeagueList() {
        return dictLeagueMapper.selectList();
    }

    @Override
    public List<DictTeam> selectDictTeamList() {
        return dictTeamMapper.selectList();
    }

    @Override
    public AjaxResult insertDictLeague(DictLeague dictLeague) {
        String db = dictLeagueMapper.selectBySp(dictLeague.getSp());
        if (StringUtils.isNotBlank(db)) {
            return AjaxResult.error("字典已经存在");
        }
        int result = dictLeagueMapper.insertDict(dictLeague);
        if (result > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    @Override
    public AjaxResult insertDictTeam(DictTeam dictTeam) {
        String db = dictTeamMapper.selectBySp(dictTeam.getSp());
        if (StringUtils.isNotBlank(db)) {
            return AjaxResult.error("字典已经存在");
        }
        int result = dictTeamMapper.insertDict(dictTeam);
        if (result > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
