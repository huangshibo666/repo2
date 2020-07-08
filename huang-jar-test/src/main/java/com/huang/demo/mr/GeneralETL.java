package com.huang.demo.mr;

import com.alibaba.fastjson.JSONObject;
import com.huang.demo.common.Constants;
import com.huang.demo.util.HdfsUtil;
import com.huang.demo.util.StrUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.metadata.HiveUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @Description:
 * @Author: huangshibo
 * @Date: 2020/6/17 10:02
 */
public class GeneralETL {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralETL.class);

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        LOGGER.info("============ main start");
        //获取输入参数
        Configuration conf = new Configuration();
        LOGGER.info("=====otherArgs " + Arrays.toString(args));

        String inPath = Constants.IN_PATH;
        String outPath = Constants.OUT_PATH;

        LOGGER.info("============inpath " + inPath);
        LOGGER.info("============outpath " + outPath);


        conf.set(Constants.HADOOP_QUEUE_NAME, Constants.HADOOP_QUEUE_VALUE);
        conf.setBoolean(Constants.MR_MAP_SPECULATIVE, false);
        conf.setBoolean(Constants.MR_REDUCE_SPECULATIVE, false);

        Job job = Job.getInstance(conf, Constants.JOB_NAME);
        job.setJarByClass(GeneralETL.class);
        job.setMapperClass(GeneralETL.TokenizerMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        job.setReducerClass(GeneralETL.TokenizerReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        job.setNumReduceTasks(Constants.REDUCE_NUM);
        job.setInputFormatClass(CombineTextInputFormat.class);
        CombineTextInputFormat.setMaxInputSplitSize(job, 250 * 1024 * 1024);
        FileInputFormat.addInputPath(job, new Path(inPath));

        if (HdfsUtil.isExistedPath(outPath)) {
            FileSystem fs = new Path(outPath).getFileSystem(conf);
            fs.delete(new Path(outPath), true);
        }
        FileOutputFormat.setOutputPath(job, new Path(outPath));

        int rc = 1;
        if (job.waitForCompletion(true)) {
            rc = 0;
        }

        if (null != job) {
            Counters counters = job.getCounters();
            CounterGroup xxzl = counters.getGroup("xxzl");
            LOGGER.info("============ 打印counter ============");
            for (Counter counter : xxzl) {
                LOGGER.info("============ " + counter.getDisplayName() + " is " + counter.getValue() + "============");
            }
        }
        System.exit(rc);
        LOGGER.info("============ main end");

    }

    private static class TokenizerMapper extends Mapper<Object, Text, Text, NullWritable>{
        Text text = new Text();
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            context.getCounter("xxzl","total_count").increment(1L);
            String line = StrUtils.replaceAllSpecial(value);
            JSONObject jsonObject;
            try {
                jsonObject = JSONObject.parseObject(line);
            } catch (Exception e){
                LOGGER.info("parse json error");
                context.getCounter("xxzl", "parse_json_error").increment(1L);
                return;
            }
            context.write(value, NullWritable.get());
        }
    }

    private static class TokenizerReducer extends Reducer<Text, NullWritable, Text, NullWritable>{
        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
        }
    }
}
