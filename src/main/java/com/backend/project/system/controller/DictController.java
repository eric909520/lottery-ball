package com.backend.project.system.controller;

import com.backend.framework.aspectj.lang.annotation.Log;
import com.backend.framework.aspectj.lang.enums.BusinessType;
import com.backend.framework.web.controller.BaseController;
import com.backend.framework.web.domain.AjaxResult;
import com.backend.framework.web.page.TableDataInfo;
import com.backend.project.system.domain.DictLeague;
import com.backend.project.system.domain.DictTeam;
import com.backend.project.system.service.IDictService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/system/dict")
public class DictController extends BaseController {
    @Resource
    private IDictService dictService;

    /**
     * 查询联赛字典列表
     */
    @PostMapping("/league/list")
    public TableDataInfo leagueList() {
        List<DictLeague> list = dictService.selectDictLeagueList();
        return getDataTable(list);
    }

    /**
     * 查询球队字典列表
     */
    @PostMapping("/team/list")
    public TableDataInfo teamList() {
        List<DictTeam> list = dictService.selectDictTeamList();
        return getDataTable(list);
    }

    /**
     * 新增联赛字典
     */
    @Log(title = "联赛字典", businessType = BusinessType.INSERT)
    @PostMapping("/league/add")
    public AjaxResult leagueAdd(@RequestBody DictLeague dictLeague) {
        return dictService.insertDictLeague(dictLeague);
    }

    /**
     * 新增球队字典
     */
    @Log(title = "球队字典", businessType = BusinessType.INSERT)
    @PostMapping("/team/add")
    public AjaxResult teamAdd(@RequestBody DictTeam dictTeam) {
        return dictService.insertDictTeam(dictTeam);
    }

}
