package com.huang.demo.util;

import java.io.*;

/**
 * @Description:
 * @Author: huangshibo
 * @Date: 2020/7/5 22:08
 */
public class FileUtil {
    public static String readFileByBytes(String fileName) throws IOException {
        File file = new File(fileName);
        InputStream in = null;
        StringBuffer sb = new StringBuffer();


        if (file.isFile() && file.exists()) { //判断文件是否存在
            System.out.println("以字节为单位读取文件内容，一次读多个字节：");
            // 一次读多个字节
            byte[] tempbytes = new byte[1024];
            int byteread = 0;
            in = new FileInputStream(file);
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((byteread = in.read(tempbytes)) != -1) {
                //  System.out.write(tempbytes, 0, byteread);
                String str = new String(tempbytes, 0, byteread);
                sb.append(str);
            }
        } else {
        }
        return sb.toString();
    }
}
