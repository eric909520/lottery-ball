package com.backend.project.system.mapper;

import com.backend.project.system.domain.DictLeague;

import java.util.List;

public interface DictLeagueMapper {

    public List<DictLeague> selectList();

    public DictLeague selectBySp(String sp);

    public DictLeague selectByHg(String hg);

    public int insertDict(DictLeague dictLeague);

}
