package com.backend.project.system.fiter;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.backend.framework.security.LoginUser;
import com.backend.framework.security.service.TokenService;
import com.backend.common.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Chars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Component
public class DruidFilter extends FilterEventAdapter {
    // TODO select 测试用
    private static final String[] LOG_KEY_WORDS = new String[]{"insert ", "update ", "delete ", "merge "};
    private static final String JOIN_CHAR = ",";
    private static final String NOT_LOGIN = "未登录";
    private static final String SLOW_SQL = "慢SQL ";
    private static final Long MILL_SECOND_NANO = 1000000L;
    // 1000毫秒以上
    @Value("${sql.slowSql}")
    private long SLOW_SQL_TIME_LIMIT;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean result) {
        // 包含标记关键词，打印sql
        if (Arrays.stream(LOG_KEY_WORDS).anyMatch(sql.toLowerCase(Locale.ROOT)::contains)) {
            LoginUser loginUser = null;
            if (ServletUtils.getRequest() != null) {
                loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            }
            long executeTime = statement.getLastExecuteTimeNano() / MILL_SECOND_NANO;
            if (statement.getParameters() == null || statement.getParameters().isEmpty()) {
                log.info("{}DruidFilter sql execute. userId: {}, sql: {} , execute time: {} ms", executeTime > SLOW_SQL_TIME_LIMIT ? SLOW_SQL : Chars.SPACE, loginUser != null ? loginUser.getUser().getUserId() : NOT_LOGIN, sql, executeTime);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                for (Map.Entry<Integer, JdbcParameter> entry : statement.getParameters().entrySet()) {
                    stringBuilder.append(entry.getValue().getValue()).append(JOIN_CHAR);
                }
                stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(JOIN_CHAR));
                log.info("{}DruidFilter sql execute. userId: {}, sql: {} , params: {}, execute time: {} ms", executeTime > SLOW_SQL_TIME_LIMIT ? SLOW_SQL : Chars.SPACE, loginUser != null ? loginUser.getUser().getUserId() : NOT_LOGIN, sql, stringBuilder, executeTime);
                stringBuilder.setLength(0);
            }
        }
        super.statementExecuteAfter(statement, sql, result);
    }
}
