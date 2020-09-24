import java.util.Objects;

import org.springframework.util.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉子转拼音处理
 *
 * @project common-utils
 * @fileName PinYin4j.java
 * @Description
 * @author light-zhang
 * @date 2019年3月29日
 * @version 1.0.0
 */
public class PinYin4j {
    // 设置汉字拼音输出的格式
    private static HanyuPinyinOutputFormat pinyinFormat = new HanyuPinyinOutputFormat();
    static {
        pinyinFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        pinyinFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        pinyinFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    /**
     * 将汉字转换为全拼
     *
     * @param src
     * @return String
     */
    public static String getPinYin(String src) {
        if (StringUtils.isEmpty(src)) {
            return null;
        }
        char[] srcChar = src.toCharArray();
        String[] temp = new String[srcChar.length];
        final StringBuffer buffer = new StringBuffer(srcChar.length);
        try {
            for (char index : srcChar) {
                if (isChinese(index)) {
                    temp = PinyinHelper.toHanyuPinyinStringArray(index, pinyinFormat);
                    if (!Objects.isNull(temp)) {
                        buffer.append(temp[0]);// 取出该汉字全拼
                    }
                } else {
                    buffer.append(Character.toString(index));
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
        }
        return buffer.toString();
    }

    /**
     * 提取每个汉字的首字母
     *
     * @return String
     */
    public static String getPinYinHeadChar(String src) {
        if (StringUtils.isEmpty(src)) {
            return null;
        }
        char[] srcChar = src.toCharArray();
        final StringBuffer buffer = new StringBuffer(srcChar.length);
        for (char index : srcChar) {
            if (isChinese(index)) {// 当是中文时
                String pinyinArray[] = PinyinHelper.toHanyuPinyinStringArray(index);
                if (!Objects.isNull(pinyinArray)) {
                    buffer.append(pinyinArray[0].charAt(0));
                }
            } else {
                buffer.append(index);
            }
        }
        return buffer.toString();
    }

    /**
     * 将字符串转换成ASCII码
     *
     * @return String
     */
    public static String getCnASCII(String src) {
        if (StringUtils.isEmpty(src)) {
            return null;
        }
        // 将字符串转换成字节序列
        byte[] bytes = src.getBytes();
        final StringBuffer buffer = new StringBuffer(bytes.length);
        for (byte index : bytes) {
            // 将每个字符转换成ASCII码
            buffer.append(Integer.toHexString(index & 0xff));
        }
        return buffer.toString();
    }

    /**
     * 判定输入的是否是汉字
     *
     * @param c 被校验的字符
     * @return true代表是汉字
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) {
            return true;
        }
        return false;
    }
}
