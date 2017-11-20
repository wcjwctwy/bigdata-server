package cn.lsmsp.bigdata.check.policy.utils;

import cn.lsmsp.bigdata.check.policy.pojo.TbEventRulerMain;
import cn.lsmsp.common.utils.SqlProvider;

import java.util.Map;

public class EventRulerSqlProvider {

    public static String  insert(Object obj){
        TbEventRulerMain tbEventRuler = (TbEventRulerMain)obj;
        String sql = null;
        try {
            sql = SqlProvider.insert(TbEventRulerCodeC.encode(tbEventRuler));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql;
    }

    public static String  update(Map<String ,Object> para){
        Object obj = para.get("obj");
        String sql = null;
        if(obj instanceof TbEventRulerMain) {

            TbEventRulerMain tbEventRuler = (TbEventRulerMain) para.get("obj");
            para.put("obj",TbEventRulerCodeC.encode(tbEventRuler));
            try {
                sql = SqlProvider.update(para);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sql;
    }

}
