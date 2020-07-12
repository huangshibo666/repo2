package com.huang.demo.mr;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huang.demo.util.FileUtil;
import com.huang.demo.util.LoggerUtil;
import com.huang.demo.util.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author: huangshibo
 * @Date: 2020/7/12 11:31
 */
public class Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws IOException {
        String content = FileUtil.readFileByBytes("E:\\files\\jsonfile/json.txt");
        String[] jsons = content.split("\n");
        for (String json : jsons) {
            Set<String> strings = StrUtils.jsonParse(json);
            System.out.println(strings);
        }

    }


}
