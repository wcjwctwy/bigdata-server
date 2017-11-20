package cn.lsmsp.common.utils;

import java.util.Map;

public class SqlCondition {
    private Object obj;
    private String order;
    private String group;
    private String limit;
    private String tableName;
    private String sum;
    private String where;

    public SqlCondition(){}
    public SqlCondition(Object obj) throws Exception{
        Map<String, String> describe = PojoUtils.transObj2Map(obj);
        this.obj=obj;
        String[] classes = describe.get("class").split("\\.");
        String className = classes[classes.length - 1];
        this.tableName = SqlProvider.transClassNameTotableName(className).substring(1);
        describe.remove("class");
        StringBuilder tempSql = new StringBuilder();
        describe.forEach((k,v)->{
            if (v!=null){
                //注意要加上'单引号
                tempSql.append(" and "+ SqlProvider.transClassNameTotableName(k)+"="+v);
            }
        });
        if(tempSql.length()!=0){
            this.where=tempSql.substring(5);
        }
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getCount() {

        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    private String count;

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
