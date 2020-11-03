package com.cetc.operlib.project.azkaban.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.TextUtils;

public class CharUtils {

    /**
     * 根据Unicode编码判断中文汉字和中文符号
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if(ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }


    /**
     * 合法的英文判断：
     * @param c
     * @return
     */
    public static boolean isEnglishByREG(char c) {
        if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ) {
            return true;
        }
        return false;
    }
    /**
     * 根据正则表达式判断部分CJK字符（CJK统一汉字）
     *
     * @param str
     * @return
     */
    public static boolean isChineseByREG(String str) {
        if(TextUtils.isEmpty(str)) return false;
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     * @param
     * @return
     */
    /*public boolean isNumeric(char str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }*/

    public static boolean isNumeric(char c){
        if (!Character.isDigit(c)) {
            return false;
        }
        return true;
    }

    /**
     * 过滤掉非中英文
     * @param str
     * @return
     */
    public static String getLetterAndChinese(String str) {
        //Log.e("AddLinkmanActivity", "要判断的字符串是:" + str);
        char[] ch = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ch.length; i++)
            if (isChineseByREG(""+ch[i]) ||
                    isEnglishByREG(
                            ch[i]))
                sb.append("" + ch[i]);
        //Log.e("AddLinkmanActivity", "最终显示的名字是:" + sb.toString());

        return sb.toString();
    }


    /**
     * 判断是否是中英文
     * @param str
     * @return
     */
    public static boolean isLetterAndChineseAndNum(String str) {
        //Log.e("AddLinkmanActivity", "要判断的字符串是:" + str);
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++)
            if (!isChineseByREG(""+ch[i]) &&
                    !isEnglishByREG(ch[i]) && !isNumeric(ch[i]) && ch[i] != '-' )
                return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isLetterAndChineseAndNum("发_顺丰iidd09"));
    }
	
	//判断是否简体  encode为GB2312
	public static boolean isSimple(String str, String encode) {
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
