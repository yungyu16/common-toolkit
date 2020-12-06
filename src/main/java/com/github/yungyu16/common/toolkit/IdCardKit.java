package com.github.yungyu16.common.toolkit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 判断18位身份证的合法性
 * </p>
 * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。<br>
 * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
 * <p>
 * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
 * </p>
 * <ol>
 * <li>第1、2位数字表示：所在省份的代码</li>
 * <li>第3、4位数字表示：所在城市的代码</li>
 * <li>第5、6位数字表示：所在区县的代码</li>
 * <li>第7~14位数字表示：出生年、月、日</li>
 * <li>第15、16位数字表示：所在地的派出所的代码</li>
 * <li>第17位数字表示性别：奇数表示男性，偶数表示女性</li>
 * <li>第18位数字是校检码，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示</li>
 * </ol>
 * <p>
 * 第十八位数字(校验码)的计算方法为：
 * <ol>
 * <li>将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2</li>
 * <li>将这17位数字和系数相乘的结果相加</li>
 * <li>用加出来和除以11，看余数是多少</li>
 * <li>余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3 2</li>
 * <li>通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2</li>
 * </ol>
 * CreatedDate: 2020/12/3
 * Author: songjialin
 */
public class IdCardKit {
    private static final DateTimeFormatter birthDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
    /**
     * 中国公民身份证号码最大长度。
     */
    private static final int LENGTH = 18;
    /**
     * 每位加权因子
     */
    private static final int[] power = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    /**
     * 省市代码表
     */
    private static final Map<String, String> cityCodes = new HashMap<String, String>();

    static {
        cityCodes.put("11", "北京");
        cityCodes.put("12", "天津");
        cityCodes.put("13", "河北");
        cityCodes.put("14", "山西");
        cityCodes.put("15", "内蒙古");
        cityCodes.put("21", "辽宁");
        cityCodes.put("22", "吉林");
        cityCodes.put("23", "黑龙江");
        cityCodes.put("31", "上海");
        cityCodes.put("32", "江苏");
        cityCodes.put("33", "浙江");
        cityCodes.put("34", "安徽");
        cityCodes.put("35", "福建");
        cityCodes.put("36", "江西");
        cityCodes.put("37", "山东");
        cityCodes.put("41", "河南");
        cityCodes.put("42", "湖北");
        cityCodes.put("43", "湖南");
        cityCodes.put("44", "广东");
        cityCodes.put("45", "广西");
        cityCodes.put("46", "海南");
        cityCodes.put("50", "重庆");
        cityCodes.put("51", "四川");
        cityCodes.put("52", "贵州");
        cityCodes.put("53", "云南");
        cityCodes.put("54", "西藏");
        cityCodes.put("61", "陕西");
        cityCodes.put("62", "甘肃");
        cityCodes.put("63", "青海");
        cityCodes.put("64", "宁夏");
        cityCodes.put("65", "新疆");
        cityCodes.put("71", "台湾");
        cityCodes.put("81", "香港");
        cityCodes.put("82", "澳门");
        cityCodes.put("91", "国外");
    }

    /**
     * 校验身份证号
     *
     * @param idCard 待验证的身份证
     * @return 是否有效的18位身份证
     */
    public static Optional<String> verify(String idCard) {
        if (StringKit.isBlank(idCard)) {
            return Optional.empty();
        }
        idCard = idCard.trim();
        if (LENGTH != idCard.length()) {
            return Optional.empty();
        }
        // 前17位
        String code17 = idCard.substring(0, 17);
        // 第18位
        char code18 = Character.toLowerCase(idCard.charAt(17));
        if (!StringKit.isNumeric(code17)) {
            return Optional.empty();
        }
        // 获取校验位
        char val = getCheckCode18(code17);
        if (val != code18) {
            return Optional.empty();
        }
        return Optional.of(idCard);

    }

    /**
     * 根据身份编号获取户籍省份
     *
     * @param idCard 身份编码
     * @return 省级编码。
     */
    public static Optional<String> getProvinceByIdCard(String idCard) {
        return substring(idCard, 0, 2)
                .map(cityCodes::get);
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idCard 身份编号
     * @return 生日(yyyyMMdd)
     */
    public static Optional<String> getBirthByIdCard(String idCard) {
        return substring(idCard, 6, 14);
    }

    /**
     * 从身份证号码中获取生日日期
     *
     * @param idCard 身份证号码
     * @return 日期
     */
    public static Optional<LocalDate> getBirthDateByIdCard(String idCard) {
        return getBirthByIdCard(idCard).map(it -> LocalDate.parse(it, birthDateFormat));
    }

    /**
     * 根据身份编号获取生日年
     *
     * @param idCard 身份编号
     * @return 生日(yyyy)
     */
    public static Optional<Short> getYearByIdCard(String idCard) {
        return substring(idCard, 6, 10)
                .map(Short::valueOf);
    }

    /**
     * 根据身份编号获取生日月
     *
     * @param idCard 身份编号
     * @return 生日(MM)
     */
    public static Optional<Short> getMonthByIdCard(String idCard) {
        return substring(idCard, 10, 12)
                .map(Short::valueOf);

    }

    /**
     * 根据身份编号获取生日天
     *
     * @param idCard 身份编号
     * @return 生日(dd)
     */
    public static Optional<Short> getDayByIdCard(String idCard) {
        return substring(idCard, 12, 14)
                .map(Short::valueOf);
    }

    /**
     * 根据身份编号获取年龄
     *
     * @param idCard 身份编号
     * @return 年龄
     */
    public static Optional<Integer> getAgeByIdCard(String idCard) {
        return getYearByIdCard(idCard).map(it -> LocalDate.now().getYear() - it);
    }

    /**
     * 根据身份编号获取性别
     *
     * @param idCard 身份编号
     * @return 性别(1 : 男 ， 0 : 女)
     */
    public static Optional<Integer> getGenderByIdCard(String idCard) {
        return substring(idCard, 16, 17)
                .map(Integer::valueOf)
                .map(it -> it % 2);
    }

    /**
     * 掩码
     *
     * @param idCard
     * @return
     */
    public static Optional<String> hide(String idCard) {
        return getBirthByIdCard(idCard).map(it -> idCard.replace(it, StringKit.repeat("*", it.length())));
    }

    public static void main(String[] args) {
        String idCard = "340102199003074097";
        System.out.println("verify:" + verify(idCard).orElseThrow(null));
        System.out.println("getProvinceByIdCard:" + getProvinceByIdCard(idCard).orElseThrow(null));
        System.out.println("getBirthByIdCard:" + getBirthByIdCard(idCard).orElseThrow(null));
        System.out.println("getBirthDateByIdCard:" + getBirthDateByIdCard(idCard).orElseThrow(null));
        System.out.println("getYearByIdCard:" + getYearByIdCard(idCard).orElseThrow(null));
        System.out.println("getMonthByIdCard:" + getMonthByIdCard(idCard).orElseThrow(null));
        System.out.println("getDayByIdCard:" + getDayByIdCard(idCard).orElseThrow(null));
        System.out.println("getAgeByIdCard:" + getAgeByIdCard(idCard).orElseThrow(null));
        System.out.println("getGenderByIdCard:" + getGenderByIdCard(idCard).orElseThrow(null));
        System.out.println("hide:" + hide(idCard).orElseThrow(null));
    }


    // ----------------------------------------------------------------------------------- Private method

    private static Optional<String> substring(String idCard, int i, int i2) {
        return verify(idCard)
                .map(it -> it.substring(i, i2));
    }

    /**
     * 获得18位身份证校验码
     *
     * @param code17 18位身份证号中的前17位
     * @return 第18位
     */
    private static char getCheckCode18(String code17) {
        int sum = getPowerSum(code17.toCharArray());
        return getCheckCode18(sum);
    }

    /**
     * 将power和值与11取模获得余数进行校验码判断
     *
     * @param iSum
     * @return 校验位
     */
    private static char getCheckCode18(int iSum) {
        switch (iSum % 11) {
            case 10:
                return '2';
            case 9:
                return '3';
            case 8:
                return '4';
            case 7:
                return '5';
            case 6:
                return '6';
            case 5:
                return '7';
            case 4:
                return '8';
            case 3:
                return '9';
            case 2:
                return 'x';
            case 1:
                return '0';
            case 0:
                return '1';
            default:
                return ' ';
        }
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     *
     * @param iArr
     * @return 身份证编码。
     */
    private static int getPowerSum(char[] iArr) {
        int iSum = 0;
        if (power.length == iArr.length) {
            for (int i = 0; i < iArr.length; i++) {
                iSum += Integer.parseInt(String.valueOf(iArr[i])) * power[i];
            }
        }
        return iSum;
    }
}
