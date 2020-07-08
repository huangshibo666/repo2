package com.huang.demo.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.GenericOptionsParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;


/**
 * @Description: Hdfs方法
 * @Author: huangshibo
 * @Date: 2020/5/8 19:42
 */
public class HdfsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HdfsUtil.class);
    private static final Configuration conf = new Configuration();

    /**
     * @param conf hadoopConf
     * @param args main方法参数
     * @return 其他参数
     * @throws IOException 异常
     */
    public static String[] getOtherArgs(Configuration conf, String[] args) throws IOException {
        return new GenericOptionsParser(conf, args).getRemainingArgs();
    }

    /**
     * 判断输入的hdfs路径是否存在
     * @param inPath hdfs输入路径
     * @return 检测结果
     */
    public static boolean isExistedPath(String inPath) {
        LOGGER.info("========== 测试hdfs路径是否存在 start" + inPath);
        boolean result = true;
        Path path = new Path(inPath);
        try {
            URI uri = URI.create("hdfs://hdp-58-cluster");
            FileSystem fs = FileSystem.get(uri, conf);
            if (!fs.exists(path)) {
                LOGGER.info("========== 路径不存在: "+ path);
                result = false;
            }
        } catch (IOException e){
            result = false;
            LOGGER.error("========== hdfs路径测试失败"+ path);
            LOGGER.error(e.toString());
        }
        LOGGER.info("========== 测试hdfs路径是否存在 end" + inPath);
        return result;
    }

    /**
     * 获取指定hdfs目录下文件
     * @param inPath
     * @return
     */
    public static FileStatus[] getHdfsFiles(String inPath){
        LOGGER.info("========== 获取指定hdfs路径下文件 start" + inPath);
        Path path = new Path(inPath);
        URI uri = URI.create("hdfs://hdp-58-cluster");
        FileStatus[] fileStatuses = null;
        try {
            FileSystem fs = FileSystem.get(uri, conf);
            fileStatuses = fs.listStatus(path);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("========== 获取hdfs目录下文件失败" + inPath);
        }
        LOGGER.info("========== 获取指定hdfs路径下文件 end" + inPath);
        return fileStatuses;
    }
}
