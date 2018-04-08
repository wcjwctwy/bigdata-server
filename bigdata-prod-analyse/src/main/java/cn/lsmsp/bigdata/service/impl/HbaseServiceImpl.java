package cn.lsmsp.bigdata.service.impl;

import cn.lsmsp.bigdata.service.HbaseService;
import cn.lsmsp.common.utils.JsonUtils;
import org.apache.avro.data.Json;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangCongJun
 * @date 2018/3/23
 * Created by WangCongJun on 2018/3/23.
 */
@Service
public class HbaseServiceImpl implements HbaseService{

    @Autowired
    private HbaseTemplate hbaseTemplate;
    @Override
    public String getByRowKey(String RowKey) {
        Object event_log = hbaseTemplate.get("event_log", RowKey, new RowMapper<Object>() {
            @Override
            public Object mapRow(Result result, int i) throws Exception {
                List<Cell> ceList = result.listCells();
                JSONObject obj = new JSONObject();
                if (ceList != null && ceList.size() > 0) {
                    for (Cell cell : ceList) {
                        obj.put(Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
                                cell.getQualifierLength()),
                                Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
                    }
                }else{
                    return null;
                }

                return obj.toString();
            }
        });

        return event_log.toString();
    }
}
