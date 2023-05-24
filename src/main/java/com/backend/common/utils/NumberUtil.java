package com.backend.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtil {

    /**
     * 保留两位小数,不进行四舍五入
     * @param d
     * @return
     */
    public static Double saveOneBitTwo(Double d){
        if (d == null) {
            return 0.0;
        }
        String number = d.toString();
        int index = number.indexOf(".");
        if (index != -1) {
            String subStr = number.substring(index + 1);
            if (subStr.length() > 2) {
                return Double.parseDouble(number.substring(0, index + 3));
            }
        }
        return d;
    }

    /**
     * double类型相加
     * @param v1
     * @param v2
     * @return
     */
    public static double doubleAdd(double v1, double v2) {
        BigDecimal b1=new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * double类型相减
     * @param v1
     * @param v2
     * @return
     */
    public static double doubleSub(double v1, double v2) {
        BigDecimal b1=new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * double类型相乘
     * @param value1
     * @param value2
     * @return
     */
    public static Double doubleMul(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 占比计算保留小数的位数方法
     * 转成百分数
     * 当前数除以总数
     * @param  num1 ,num2  num1/num2
     * @return  rate  保留2位小数的
     */
    public static String divisionRate(int num1,int num2){
        String rate="0.00%";
        //定义格式化起始位数
        String format="0.00";
        if(num2 != 0 && num1 != 0){
            DecimalFormat dec = new DecimalFormat(format);
            rate =  dec.format((double) num1 / num2*100)+"%";
            while(true){
                if(rate.equals(format+"%")){
                    format=format+"0";
                    DecimalFormat dec1 = new DecimalFormat(format);
                    rate =  dec1.format((double) num1 / num2*100)+"%";
                }else {
                    break;
                }
            }
        }else if(num1 != 0 && num2 == 0){
            rate = "100%";
        }
        return rate;
    }


    /**
     * 把百分比转为字符串类型的小数  保留两位小数
     */
    public static BigDecimal perToDecimal(String percent){
        String decimal = percent.substring(0,percent.indexOf("%"));
        BigDecimal bigDecimal = new BigDecimal(decimal);
        bigDecimal.divide(new BigDecimal("100"), 4, BigDecimal.ROUND_HALF_UP);
        return bigDecimal;
    }

    /**
     * 9-99随机数
     * @return
     */
    public static int getRandom9() {
        return (int) (Math.random()*90 + 9);
    }

}
