package com.backend.common.utils.sql;

import com.backend.common.utils.StringUtils;

/**
 * sql操作工具类
 *
 * @author
 */
public class SqlUtil {
    /**
     * 仅支持字母、数字、下划线、空格、逗号（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,]+";

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value) {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value)) {
            return StringUtils.EMPTY;
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }

    /**
     * 生成交易号
     * @return
     */
    public static Long generateTranNo() {
        int random = (int) (Math.random() * 900) + 100;
        return System.currentTimeMillis() + random;
    }

    /**
     * 生成18位数id
     * @return
     */
    public static Long generateId() {
        int senvenRandom = (int) (Math.random() * 90000) + 10000;
        return System.currentTimeMillis()*100000+senvenRandom;
    }

}
