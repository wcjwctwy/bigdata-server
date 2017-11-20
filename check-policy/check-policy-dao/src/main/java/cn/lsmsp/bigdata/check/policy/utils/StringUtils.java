package cn.lsmsp.bigdata.check.policy.utils;

public class StringUtils {
    public static String upCaseFirst(String str){
        String firstUp = str.substring(0, 1).toUpperCase();
        return firstUp+str.substring(1);
    }
    public static String lowCaseFirst(String str){
        String firstUp = str.substring(0, 1).toLowerCase();
        return firstUp+str.substring(1);
    }

    public static String getMethodName(String str){
        String firstUp = str.substring(0, 1).toUpperCase();
        return "get"+firstUp+str.substring(1);
    }
    public static String setMethodName(String str){
        String firstUp = str.substring(0, 1).toUpperCase();
        return "set"+firstUp+str.substring(1);
    }
}
