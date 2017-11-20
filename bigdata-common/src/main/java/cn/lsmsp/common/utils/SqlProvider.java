package cn.lsmsp.common.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.Map;

public class SqlProvider {


    public static String group(SqlCondition sqlCondition) throws Exception{
        String group = sqlCondition.getGroup();
        String tableName = sqlCondition.getTableName();
        String sum = sqlCondition.getSum();
        String count = sqlCondition.getCount();
        String where = sqlCondition.getWhere();
        String order = sqlCondition.getOrder();
        StringBuilder sql = new StringBuilder("select ");
        if(!StringUtils.isEmpty(group)){
            sql.append(" ").append(group).append(",");
        }
        if(StringUtils.isEmpty(sum)&&StringUtils.isEmpty(count)){
            sql= new StringBuilder( sql.substring(0,sql.length()-1)+" ");
        }
        if(!StringUtils.isEmpty(sum)){
           sql.append(sum).append(",");
        }
        if(!StringUtils.isEmpty(count)){
            sql.append(count);
        }else{
            sql = new StringBuilder( sql.substring(0,sql.length()-1)+" ");
        }
        if(!StringUtils.isEmpty(tableName)){
            sql.append(" from ").append(tableName).append(" ");
        }

        if(!StringUtils.isEmpty(where)){
            sql.append("where ").append(where).append(" ");
        }

        if(!StringUtils.isEmpty(group)){
            sql.append("group by ").append(group).append(" ");
        }
        if(!StringUtils.isEmpty(order)){
            sql.append("order by ").append(order).append(" ");
        }
        return sql.toString();
    }

    public static String  insert(Object obj) throws Exception{
        Map<String, String> describe = BeanUtils.describe(obj);
        String[] classes = describe.get("class").split("\\.");
        String className = classes[classes.length - 1];
        String tableName = transClassNameTotableName(className).substring(1);
        describe.remove("class");
        StringBuilder filedSql = new StringBuilder();
        StringBuilder valueSql = new StringBuilder();
        describe.forEach((k,v)->{
            if (v!=null){
                filedSql.append(","+transClassNameTotableName(k)+"");
                //注意要加上'单引号
                valueSql.append(",'"+v+"'");
            }
        });
        //去除第一个逗号
        String sql = "insert into "+tableName+"("+filedSql.substring(1)+")values("+valueSql.substring(1)+")";
//        String sql = "insert into tb_sfinfo("+filedSql.substring(1)+")values("+valueSql.substring(1)+")";
        return sql;
    }

    public static String  select(Object obj) throws Exception{
        Map<String, String> describe = BeanUtils.describe(obj);
        String[] classes = describe.get("class").split("\\.");
        String className = classes[classes.length - 1];
        String tableName = transClassNameTotableName(className).substring(1);
        describe.remove("class");
        StringBuilder tempSql = new StringBuilder();
        describe.forEach((k,v)->{
            if (v!=null){

                //注意要加上'单引号
                tempSql.append(" and "+ transClassNameTotableName(k)+"="+"'"+v+"'");
            }
        });
        if(tempSql.length()==0){
            return "select * from "+tableName;
        }else{
            //去除第一个" and "
            return "select * from "+tableName+" where " + tempSql.substring(5);
        }

    }

    public static String  delete(Object obj) throws Exception{
        Map<String, String> describe = BeanUtils.describe(obj);
        String[] classes = describe.get("class").split("\\.");
        String className = classes[classes.length - 1];
        String tableName = transClassNameTotableName(className).substring(1);
        describe.remove("class");
        StringBuilder tempSql = new StringBuilder();
        describe.forEach((k,v)->{
            if (v!=null){
                //注意要加上'单引号
                tempSql.append(" and "+ transClassNameTotableName(k)+"="+"'"+v+"'");
            }
        });
        if(tempSql.length()==0){
            throw new RuntimeException("删除条件不可为空！");
        }else{
            //去除第一个" and "
            return "delete from "+tableName+" where " + tempSql.substring(5);
        }

    }



    public static String  select2(SqlCondition sqlCondition) throws Exception{
        Map<String, String> describe = BeanUtils.describe(sqlCondition.getObj());
        String[] classes = describe.get("class").split("\\.");
        String className = classes[classes.length - 1];
        String tableName = transClassNameTotableName(className).substring(1);
        describe.remove("class");
        StringBuilder tempSql = new StringBuilder();
        String sql = null;
        describe.forEach((k,v)->{
            if (v!=null){

                //注意要加上'单引号
                tempSql.append(" and "+ transClassNameTotableName(k)+"="+"'"+v+"'");
            }
        });
        if(tempSql.length()==0){
            sql= "select * from "+tableName;
        }else{
            //去除第一个" and "
            sql= "select * from "+tableName+" where " + tempSql.substring(5);
        }

        String group = "";
        if(!StringUtils.isEmpty(sqlCondition.getGroup())){
            group=" "+sqlCondition.getGroup();
        }

        String order = "";
        if(!StringUtils.isEmpty(sqlCondition.getOrder())){
            order=" "+sqlCondition.getOrder();
        }

        String limit = "";
        if(!StringUtils.isEmpty(sqlCondition.getLimit())){
            limit=" "+sqlCondition.getLimit();
        }
        sql = sql+group+order+limit;
        return sql;
    }


    public static String  update(Map<String ,Object> para) throws Exception{
        Map<String, String> describe = BeanUtils.describe(para.get("obj"));
        String[] classes = describe.get("class").split("\\.");
        String className = classes[classes.length - 1];
        String tableName = transClassNameTotableName(className).substring(1);
        describe.remove("class");
        StringBuilder tempSql = new StringBuilder();
        describe.forEach((k,v)->{
            if (v!=null){

                //注意要加上'单引号
                tempSql.append( ","+transClassNameTotableName(k)+"="+"'"+v+"'");
            }
        });
        //去除第一个" and "
        String sql = "update "+tableName+" set " + tempSql.substring(1)+" where "+para.get("condition");
        return sql;
    }

    /**
     * 将字符串中的大写字母改成“_+小写”
     * @param className
     * @return
     */
    public static String transClassNameTotableName(String className){
        StringBuilder tableName = new StringBuilder();
        char[] ss = className.toCharArray();
        for(char s:ss){
            if(Character.isLowerCase(s)||Character.isDigit(s))tableName.append(String.valueOf(s));
                else tableName.append("_"+String.valueOf(s).toLowerCase());

        }
        return tableName.toString();
    }
}
