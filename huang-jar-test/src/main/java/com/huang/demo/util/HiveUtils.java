package com.huang.demo.util;

import com.alibaba.fastjson.JSONObject;
import com.huang.demo.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: huangshibo
 * @Date: 2020/6/12 12:31
 */
public class HiveUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HiveUtils.class);

    public static String getHiveSql(String json) {
        String sql;
        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(json);
        } catch (Exception e){
            return null;
        }

        if (StrUtils.isNull(jsonObject)){
            return null;
        }

        String hiveTable = jsonObject.getString(Constants.HIVE_TABLE);
        String date = jsonObject.getString(Constants.HIVE_DATE);
        String hour = jsonObject.getString(Constants.HIVE_HOUR);

        if (StrUtils.isEmpty(hiveTable) || StrUtils.isEmpty(date)){
            return null;
        }

        if (StrUtils.isEmpty(hour)){
            sql = "alter table hdp_ubu_xxzl_defaultdb." + hiveTable + " add if not exists partition(dt='" + date + "');";
        } else {
            sql = "alter table hdp_ubu_xxzl_defaultdb." + hiveTable + " add if not exists partition(dt='" + date + "',hour='" + hour + "');";
        }
        LOGGER.info("============ sql: " + sql);
        return sql;
    }

    public static void execShell(String sql) throws IOException {
        List<String> command = new ArrayList<String>();
        command.add(Constants.HIVE);
        command.add(Constants.HIVE_ARGUMENT);
        command.add(sql);
        ProcessBuilder hiveProcessBuilder = new ProcessBuilder(command);
        BufferedReader br = new BufferedReader(new InputStreamReader(
                hiveProcessBuilder.start().getInputStream()));
        String data;
        while ((data = br.readLine()) != null) {
            LOGGER.info(data);
        }
    }
}
