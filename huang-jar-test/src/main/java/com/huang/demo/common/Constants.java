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
    String HADOOP_QUEUE_VALUE = "";
    String MR_MAP_SPECULATIVE = "mapreduce.map.speculative";
    String MR_REDUCE_SPECULATIVE = "mapreduce.reduce.speculative";
    Integer SPLIT_SIZE = 250 * 1024 * 1024;
    String IN_PATH = "";
    String OUT_PATH = "";
    String JOB_NAME = "";
    Integer REDUCE_NUM = 100;

    /**
     * hive命令参数
     */
    String HIVE = "hive";
    String HIVE_ARGUMENT = "-e";
    String HIVE_DATE = "hiveDate";
    String HIVE_HOUR = "hiveHour";
    String HIVE_TABLE = "hiveTable";
}
