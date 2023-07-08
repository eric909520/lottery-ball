package com.backend.project.system.mapper;

import com.backend.framework.aspectj.lang.annotation.DataSource;
import com.backend.framework.aspectj.lang.enums.DataSourceType;
import com.backend.project.system.domain.AppConfig;
import com.backend.project.system.domain.NotifyMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    NotifyMsg findMsgByContion(@Param("type") String type,@Param("betId") Long betId);
}
