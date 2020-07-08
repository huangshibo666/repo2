//package com.huang.demo.util;
//
//import com.huang.demo.common.Constants;
//import org.apache.hadoop.hive.ql.parse.ASTNode;
//import org.apache.hadoop.hive.ql.parse.ParseDriver;
//import org.apache.hadoop.hive.ql.parse.ParseException;
//import scala.actors.migration.pattern;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * @Description:
// * @Author: huangshibo
// * @Date: 2020/7/5 20:26
// */
//public class HiveSqlParser {
////    切 SQL 语句. 使用 ; 将文件中的语句切成独立的 SQL
////    识别 SET 语句
////    如果是设置 Hive 参数, 直接略过
////    如果是设置参数, 保存变量的值, 用于后续 SQL 语句的变量替换
////    识别 USE 语句, 保存当前所在 database 的 context. 当遇到直接使用 Table 名而不是 db.表名 的时候添加当前 database 的名称
////    识别正式的 SQL 语句, 根据当前 context 中存储的变量替换 SQL 类似 ${variable_name} 字符串
////    执行 SQL 分析流程, 将结果保存
//
//    public static void main(String[] args) throws ParseException, IOException {
//        String content = FileUtil.readFileByBytes("E:/files/sql-file/allhuesql.txt");
////        System.out.println(content);
//
//        String regex = "--.*";
//        String regex2 = "set .*;";
//        String regex3 = "use .*;";
//        String regex4= "\\$\\{.*?\\}";
//        ParseDriver pd = new ParseDriver();
//        String sqlSample = content.toLowerCase()
//                .replaceAll(regex,"")
//                .replaceAll(regex2, "")
//                .replaceAll(regex3, "")
//                .replaceAll(regex4,"5");
////                .replaceAll(";","");
////        System.out.println(sqlSample);
//        String[] sqls = sqlSample.split(";");
//        for (String sql : sqls) {
//            ASTNode tree = pd.parse(sql);
//            String treeStr = tree.toStringTree().toLowerCase();
////            System.out.println(treeStr);
//            List<String> tableList = getTableList(treeStr);
//            System.out.println(tableList);
//        }
//
//
//
//
//    }
//
//
//    /**
//     * 递归截取字符串获取表名
//     * @param strTree
//     * @return
//     */
//    public static List<String> getTableList(String strTree){
//        String regex = "tok_tabname .*?\\)";
//        Set<String> set = new HashSet<>();
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(strTree);
//        while (matcher.find()){
//            String res = "";
//            String str = matcher.group().replace(")","");
//            String[] splits = str.split(" ");
//            if (splits.length > 1){
//                for (int i=1; i<splits.length; i++){
//                    if (i == splits.length - 1){
//                        res += splits[i];
//                    } else{
//                        res += splits[i] + ".";
//                    }
//                }
//            }
//            set.add(res);
//        }
//        return new ArrayList<>(set);
//    }
//
//}
