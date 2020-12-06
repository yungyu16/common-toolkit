package com.github.yungyu16.framework.toolkit;

import com.google.common.base.Verify;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * CreatedDate: 2020/9/17
 * Author: songjialin
 */
public class BigDecimalKit {
    private static final BigDecimal _100 = new BigDecimal(100);

    public static BigDecimal null2Zero(BigDecimal raw) {
        if (raw == null) {
            return BigDecimal.ZERO;
        }
        return raw;
    }

    /**
     * 四舍五入保留两位小数
     *
     * @return
     */
    public static BigDecimal halfDown2(BigDecimal raw) {
        if (raw == null) {
            return BigDecimal.ZERO;
        }
        return raw.setScale(2, RoundingMode.HALF_DOWN);
    }

    public static BigDecimal fen2Yuan(BigDecimal raw) {
        if (raw == null) {
            return BigDecimal.ZERO;
        }
        return raw.divide(_100, 6, RoundingMode.HALF_DOWN);
    }

    public static BigDecimal yuan2Fen(BigDecimal raw) {
        if (raw == null) {
            return BigDecimal.ZERO;
        }
        return raw.multiply(_100);
    }

    public static boolean equal(BigDecimal left, BigDecimal right) {
        return compareTo(left, right) == 0;
    }

    public static boolean between(BigDecimal source, BigDecimal left, BigDecimal right, boolean leftIntervalClosed, boolean rightIntervalClosed) {
        boolean leftCompare = leftIntervalClosed ? compareTo(source, left) >= 0 : compareTo(source, left) > 0;
        boolean rightCompare = rightIntervalClosed ? compareTo(source, right) <= 0 : compareTo(source, right) < 0;
        return leftCompare && rightCompare;
    }

    public static boolean between(BigDecimal source, String left, String right, boolean leftIntervalClosed, boolean rightIntervalClosed) {
        Verify.verifyNotNull(left);
        Verify.verifyNotNull(right);
        BigDecimal min = new BigDecimal(left);
        BigDecimal max = new BigDecimal(right);
        boolean leftCompare = leftIntervalClosed ? compareTo(source, min) >= 0 : compareTo(source, min) > 0;
        boolean rightCompare = rightIntervalClosed ? compareTo(source, max) <= 0 : compareTo(source, max) < 0;
        return leftCompare && rightCompare;
    }

    public static boolean lessZero(BigDecimal left) {
        return compareTo(left, BigDecimal.ZERO) < 0;
    }

    public static boolean leZero(BigDecimal left) {
        return compareTo(left, BigDecimal.ZERO) <= 0;
    }

    public static boolean greaterZero(BigDecimal left) {
        return compareTo(left, BigDecimal.ZERO) > 0;
    }

    public static boolean geZero(BigDecimal left) {
        return compareTo(left, BigDecimal.ZERO) >= 0;
    }

    private static int compareTo(BigDecimal left, BigDecimal right) {
        Verify.verifyNotNull(left);
        Verify.verifyNotNull(right);
        return left.compareTo(right);
    }
}