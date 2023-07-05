package com.backend.project.system.mapper;

import com.backend.project.system.domain.DictTeam;

import java.util.List;

public interface DictTeamMapper {

    public List<DictTeam> selectList();

    public DictTeam selectBySp(String sp);

    public DictTeam selectByHg(String hg);

    public int insertDict(DictTeam dictTeam);

}
