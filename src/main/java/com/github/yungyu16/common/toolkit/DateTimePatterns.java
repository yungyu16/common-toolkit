package com.github.yungyu16.common.toolkit;

import java.time.format.DateTimeFormatter;

/**
 * CreatedDate: 2020/9/17
 * Author: songjialin
 */
public interface DateTimePatterns {
    DateTimeFormatter YYYYMM = DateTimeFormatter.ofPattern("yyyyMM");
    DateTimeFormatter MM_DD = DateTimeFormatter.ofPattern("MM-dd");
    DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    DateTimeFormatter YY_MM = DateTimeFormatter.ofPattern("YY-MM");
    DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter YYYY_MM_DD_HH_MM = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    DateTimeFormatter YYYYMMDDHHMMSSSSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
}
