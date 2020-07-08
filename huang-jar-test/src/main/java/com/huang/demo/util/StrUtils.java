package com.huang.demo.util;

import org.apache.hadoop.io.Text;

/**
 * @Description:
 * @Author: huangshibo
 * @Date: 2020/5/26 10:12
 */
public class StrUtils {

    public static boolean isNull(Object obj){
        return null == obj;
    }

    public static boolean isNotNull(Object obj){
        return null != obj;
    }

    public static boolean isEmpty(String str){
        return str == null || "".equals(str);
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    public  static boolean isArrayEmpty(String str){
        return str == null || "".equals(str) || str.length() < 3;
    }

    public  static boolean isNotArrayEmpty(String str){
        return !isArrayEmpty(str);
    }

    public static String replaceAllSpecial(Text value) {
        String a = value.toString();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            char s = a.charAt(i);
            if (s == '\r') {
                sb.append('r');
            } else if (s == '\n') {
                sb.append('n');
            } else if (s == '\\') {
                int inc = 0;
                for (int j = i + 1; j < a.length(); j++) {
                    char ss = a.charAt(j);
                    if (ss != '\\') {
                        if (ss == '\"') {
                            inc = j - i;
                            for (int h = i; h < j; h++) {
                                sb.append('\\');
                            }
                            sb.append('\"');
                        }
                        break;
                    }
                }
                i += inc;
            } else {
                sb.append(a.charAt(i));
            }
        }
//        //贪婪匹配 {...}
//        String regex = "\\{.*\\}";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(sb);
//        String json = "";
//        while (matcher.find()) {
//            json = matcher.group();
//        }
        return sb.toString();
    }


}
