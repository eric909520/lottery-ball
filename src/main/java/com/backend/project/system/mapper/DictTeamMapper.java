package com.backend.project.system.mapper;

import com.backend.project.system.domain.DictTeam;

import java.util.List;

public interface DictTeamMapper {

    public List<DictTeam> selectList();

    public String selectBySp(String sp);

    public String selectByHg(String hg);

    public int insertDict(DictTeam dictTeam);

}
