package cn.lsmsp.common.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.stream.Collectors;

public class SqlUtils {

    /**
     * 将java中的驼峰转换成_格式的
     * @param className
     * @return
     */
    public static String transHump(String className){
        StringBuilder tableName = new StringBuilder();
        char[] ss = className.toCharArray();
        for(char s:ss){
            if(Character.isLowerCase(s)||Character.isDigit(s)){
                tableName.append(String.valueOf(s));
            }
            else {
                tableName.append("_"+String.valueOf(s).toLowerCase());
            }

        }
        return tableName.toString();
    }

    /**
     * 将传入的对象
     * @param obj
     * @return
     */
    public static String[] getConditions(Object obj,String paramName){
        Map<String, String> describe;
        try {
            describe = BeanUtils.describe(obj);
            List<String> result=new ArrayList<>();
            describe.forEach((k,v)->{
                if(!"class".equals(k)&&v!=null){
                    result.add(SqlUtils.transHump(k) + "=#{"+paramName+"." + k + "}") ;
                }
            });
            return result.toArray(new String[result.size()]);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new String[]{};
    }

    public static String toLowerCaseTheFirst(String str){
        return str.substring(0,1).toLowerCase().concat(str.substring(1));
    }

    /**
     * 获取类字段对应的表字段
     * @param obj
     * @param removeFields
     * @return
     */
    public static String[][] getColums(Object obj,String ... removeFields){
        String[][] objects = new String[2][];
        try {

            Map<String, String> describe = BeanUtils.describe(obj);
            describe.remove("class");
            for(String s:removeFields) {
                describe.remove(s);
            }
            Set<String> strings = describe.keySet();
            List<String> collect = strings.stream().map(SqlUtils::transHump).collect(Collectors.toList());
            objects[0]=collect.toArray(new String[collect.size()]);

            Collection<String> values = describe.values();
            ArrayList<String> strings1 = new ArrayList<>(values);
            String[] objects1 = strings1.toArray(new String[strings1.size()]);
            objects[1]=objects1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return objects;
    }

}
