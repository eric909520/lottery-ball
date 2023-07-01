package com.backend.common.utils;

import java.math.BigDecimal;

/**
 * com.hzfh.common.utils
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精
 * 确的浮点数运算，包括加减乘除和四舍五入。
 * @Project: ideawork
 * @Author: rencc
 * @Description:
 * @Date: 2017/11/8 9:28
 * @Source: Created with IntelliJ IDEA.
 */
public class CalcUtil {
    private static final int DEF_DIV_SCALE = 2; //这个类不能实例化
    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static Double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static Double add(double v1,double v2,double v3){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        return b1.add(b2).add(b3).doubleValue();
    }

    public static Double add(double v1,double v2,double v3, double v4){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        return b1.add(b2).add(b3).add(b4).doubleValue();
    }

    public static Double add(double v1,double v2,double v3, double v4, double v5){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        BigDecimal b5 = new BigDecimal(Double.toString(v5));
        return b1.add(b2).add(b3).add(b4).add(b5).doubleValue();
    }

    public static Double add(double v1,double v2,double v3, double v4, double v5, double v6){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        BigDecimal b5 = new BigDecimal(Double.toString(v5));
        BigDecimal b6 = new BigDecimal(Double.toString(v6));
        return b1.add(b2).add(b3).add(b4).add(b5).add(b6).doubleValue();
    }

    public static Double add(double v1,double v2,double v3, double v4, double v5, double v6, double v7){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        BigDecimal b5 = new BigDecimal(Double.toString(v5));
        BigDecimal b6 = new BigDecimal(Double.toString(v6));
        BigDecimal b7 = new BigDecimal(Double.toString(v7));
        return b1.add(b2).add(b3).add(b4).add(b5).add(b6).add(b7).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static Double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static Double sub(double v1,double v2,double v3){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        return b1.subtract(b2).subtract(b3).doubleValue();
    }

    public static Double sub(double v1,double v2,double v3,double v4){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        return b1.subtract(b2).subtract(b3).subtract(b4).doubleValue();
    }

    public static Double sub(double v1,double v2,double v3,double v4,double v5){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        BigDecimal b5 = new BigDecimal(Double.toString(v5));
        return b1.subtract(b2).subtract(b3).subtract(b4).subtract(b5).doubleValue();
    }

    public static Double sub(double v1,double v2,double v3,double v4,double v5, double v6){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        BigDecimal b5 = new BigDecimal(Double.toString(v5));
        BigDecimal b6 = new BigDecimal(Double.toString(v6));
        return b1.subtract(b2).subtract(b3).subtract(b4).subtract(b5).subtract(b6).doubleValue();
    }

    public static Double sub(double v1,double v2,double v3,double v4,double v5, double v6, double v7){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        BigDecimal b5 = new BigDecimal(Double.toString(v5));
        BigDecimal b6 = new BigDecimal(Double.toString(v6));
        BigDecimal b7 = new BigDecimal(Double.toString(v7));
        return b1.subtract(b2).subtract(b3).subtract(b4).subtract(b5).subtract(b6).subtract(b7).doubleValue();
    }

    public static Double sub(double v1,double v2,double v3,double v4,double v5, double v6, double v7, double v8){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        BigDecimal b5 = new BigDecimal(Double.toString(v5));
        BigDecimal b6 = new BigDecimal(Double.toString(v6));
        BigDecimal b7 = new BigDecimal(Double.toString(v7));
        BigDecimal b8 = new BigDecimal(Double.toString(v8));
        return b1.subtract(b2).subtract(b3).subtract(b4).subtract(b5).subtract(b6).subtract(b7).subtract(b8).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static Double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static Double div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static Double div(double v1,double v2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static Double round(double v,int scale){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static String divide(double v1,double v2, int scale){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).toString();
    }

}
