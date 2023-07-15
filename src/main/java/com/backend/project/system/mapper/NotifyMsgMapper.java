package com.backend.project.system.mapper;

import com.backend.project.system.domain.NotifyMsg;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author
 */
public interface NotifyMsgMapper {
    /**
     * 插入消息信息
     * @param msg
     */
    void  insertNotifyMsg(NotifyMsg msg);

    NotifyMsg findMsgByCondition(@Param("type") String type, @Param("betId") Long betId, @Param("handicap") String handicap);
}
