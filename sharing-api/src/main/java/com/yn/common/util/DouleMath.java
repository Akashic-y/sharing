package com.yn.common.util;

import java.math.BigDecimal;

/**
 * @author ljw
 * <p>
 * Double 计算工具类
 */
public class DouleMath {

    // 默认除法运算精度
    private static final int DEFAULT_DIV_SCALE = 10;

    /**
     * 提供精确的加法运算。
     *
     * @param Dou1
     * @param Dou2
     * @return 两个参数的和
     */
    public static double add(double Dou1, double Dou2) {
        BigDecimal Big1 = new BigDecimal(Double.toString(Dou1));
        BigDecimal Big2 = new BigDecimal(Double.toString(Dou2));
        return Big1.add(Big2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param Dou1
     * @param Dou2
     * @return 两个参数的差
     */
    public static double subtract(double Dou1, double Dou2) {
        BigDecimal Big1 = new BigDecimal(Double.toString(Dou1));
        BigDecimal Big2 = new BigDecimal(Double.toString(Dou2));
        return Big1.subtract(Big2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param Dou1
     * @param Dou2
     * @return 两个参数的积
     */
    public static double multiply(double Dou1, double Dou2) {
        BigDecimal Big1 = new BigDecimal(Double.toString(Dou1));
        BigDecimal Big2 = new BigDecimal(Double.toString(Dou2));
        return Big1.multiply(Big2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * <p>
     * 小数点以后10位，以后的数字四舍五入,舍入模式采用ROUND_HALF_EVEN
     *
     * @param Dou1
     * @param Dou2
     * @return 两个参数的商
     */
    public static double divide(double Dou1, double Dou2) {
        return divide(Dou1, Dou2, DEFAULT_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * <p>
     * 定精度，以后的数字四舍五入。舍入模式采用ROUND_HALF_EVEN
     *
     * @param Dou1
     * @param Dou2
     * @param scale 表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double divide(double Dou1, double Dou2, int scale) {
        return divide(Dou1, Dou2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * <p>
     * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
     *
     * @param Dou1
     * @param Dou2
     * @param scale      表示需要精确到小数点以后几位
     * @param round_mode 表示用户指定的舍入模式
     * @return 两个参数的商
     */
    public static double divide(double Dou1, double Dou2, int scale, int round_mode) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "精度必须大于等于0");
        }
        BigDecimal Big1 = new BigDecimal(Double.toString(Dou1));
        BigDecimal Big2 = new BigDecimal(Double.toString(Dou2));
        return Big1.divide(Big2, scale, round_mode).doubleValue();
    }
}
