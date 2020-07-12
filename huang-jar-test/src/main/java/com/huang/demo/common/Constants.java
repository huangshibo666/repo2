package com.huang.demo.common;

/**
 * @Description:
 * @Author: huangshibo
 * @Date: 2020/6/24 15:40
 */
public interface Constants {
    /**
     * mr参数
     */
    String HADOOP_QUEUE_NAME = "mapred.job.queue.name";
    String HADOOP_QUEUE_VALUE = "root.offline.hdp_ubu_xxzl.normal";
    String MR_MAP_SPECULATIVE = "mapreduce.map.speculative";
    String MR_REDUCE_SPECULATIVE = "mapreduce.reduce.speculative";
    Integer SPLIT_SIZE = 250 * 1024 * 1024;
    String IN_PATH = "/home/hdp_ubu_xxzl/warehouse/hdp_ubu_xxzl_defaultdb/dw_v_authcenter_realdw_json/20200620/*/";
    String OUT_PATH = "/home/hdp_ubu_xxzl/warehouse/hdp_ubu_xxzl_defaultdb/huangshibo/test4";
    String JOB_NAME = "test_wordcount";
    Integer REDUCE_NUM = 1;

    /**
     * hive命令参数
     */
    String HIVE = "hive";
    String HIVE_ARGUMENT = "-e";
    String HIVE_DATE = "hiveDate";
    String HIVE_HOUR = "hiveHour";
    String HIVE_TABLE = "hiveTable";
}
