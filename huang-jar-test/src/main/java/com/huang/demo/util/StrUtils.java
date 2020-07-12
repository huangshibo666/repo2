package com.huang.demo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.io.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: huangshibo
 * @Date: 2020/5/26 10:12
 */
public class StrUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(StrUtils.class);

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
        //贪婪匹配 {...}
        String regex = "\\{.*\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sb);
        String json = "";
        while (matcher.find()) {
            json = matcher.group();
        }
        return json;
    }

    public static Set<String> jsonParse(String json) {
        Set<String> set = new HashSet<>();
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(json);
        } catch (Exception e) {
            LoggerUtil.printLoggerInfo(LOGGER, "json parse fail", e.toString());
        }
        if (StrUtils.isNull(jsonObject)){
            return null;
        }
        String strategyResultArr = jsonObject.getString("hunters_strategyrheckresult");
        JSONArray strategyResultJsonArr = JSONObject.parseArray(strategyResultArr);
        if (StrUtils.isArrayEmpty(strategyResultArr)){
            return null;
        }
        Iterator<Object> iterator = strategyResultJsonArr.iterator();
        while (iterator.hasNext()){
            JSONObject strategyResult = (JSONObject)iterator.next();
//            System.out.println(strategyResult);
            String rule = strategyResult.getString("rule");
//            System.out.println(rule);
            if (StrUtils.isEmpty(rule)){
                continue;
            }
            JSONArray ruleJsonArr = JSONObject.parseArray(rule);
//            System.out.println(ruleJsonArr);
            if (StrUtils.isNotNull(ruleJsonArr)){
                Iterator<Object> iterator1 = ruleJsonArr.iterator();
                while (iterator1.hasNext()){
                    JSONObject ruleJson = (JSONObject)iterator1.next();
                    String toolid = ruleJson.getString("toolid");
                    set.add(toolid);
                }
            }

        }
        return set;
    }


}
