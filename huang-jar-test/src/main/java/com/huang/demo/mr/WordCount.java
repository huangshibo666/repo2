package com.huang.demo.mr;

import java.io.IOException;
import java.util.Arrays;

import com.huang.demo.common.Constants;
import com.huang.demo.util.HdfsUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordCount {
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
        job.setJarByClass(WordCount.class);
        job.setMapperClass(WordCount.TokenizerMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        job.setReducerClass(WordCount.TokenizerReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        job.setNumReduceTasks(Constants.REDUCE_NUM);
        job.setInputFormatClass(CombineTextInputFormat.class);
        CombineTextInputFormat.setMaxInputSplitSize(job, Constants.SPLIT_SIZE);
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

    public static class TokenizerMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

        LongWritable valueOut = new LongWritable(1);

        Text keyOut = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] split = value.toString().split(" ");

            for (String str : split) {
                keyOut.set(str);
                context.write(keyOut, valueOut);
            }
        }

    }

    public static class TokenizerReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

        LongWritable outValue = new LongWritable();

        @Override
        protected void reduce(Text keyIn, Iterable<LongWritable> valueIn, Context context)
                throws IOException, InterruptedException {
            long sum = 0L;
            for (LongWritable num : valueIn) {
                sum += num.get();
            }
            outValue.set(sum);
            context.write(keyIn, outValue);
        }
    }
}
