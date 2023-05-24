package com.backend.project.system.mapper;

import com.backend.project.system.domain.SysNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知公告表 数据层
 *
 * @author
 */
public interface SysNoticeMapper {
    /**
     * 查询公告信息
     *
     * @param id 公告ID
     * @return 公告信息
     */
    public SysNotice selectNoticeById(@Param("id") Long id);

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    public List<SysNotice> selectNoticeList(SysNotice notice);

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    public int insertNotice(SysNotice notice);

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    public int updateNotice(SysNotice notice);

    /**
     * 批量删除公告
     *
     * @param id 公告ID
     * @return 结果
     */
    public int deleteNoticeById(Long id);

    /**
     * 批量删除公告信息
     *
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    public int deleteNoticeByIds(Long noticeIds);
}
