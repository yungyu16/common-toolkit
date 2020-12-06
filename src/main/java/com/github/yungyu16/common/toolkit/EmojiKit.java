package com.github.yungyu16.common.toolkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description：判断字符串中是否包含emoj表情及过滤
 */
public class EmojiKit {
    private static final String EMOJI_PATTERN = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
    private static final Pattern pattern = Pattern.compile(EMOJI_PATTERN);

    /**
     * 判断是否包含emoji
     *
     * @param content
     * @return
     */
    public static boolean containsEmoji(String content) {
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }

    /**
     * 替换字符串中的emoji字符
     *
     * @param str
     * @param replacement
     * @return
     */
    public static String replaceEmoji(String str, String replacement) {
        if (StringKit.isBlank(str)) {
            return str;
        }
        if (containsEmoji(str)) {
            str = str.replaceAll(EMOJI_PATTERN, replacement);
        }
        return str;
    }

}