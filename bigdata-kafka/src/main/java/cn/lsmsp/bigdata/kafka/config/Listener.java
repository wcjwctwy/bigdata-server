package cn.lsmsp.bigdata.kafka.config;

import cn.lsmsp.common.utils.JsonUtils;
import cn.lsmsp.sync.protobuf.alarm.AlarmProtoBuf;
import com.google.protobuf.Descriptors;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.kafka.annotation.KafkaListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Listener {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<String> whiteList;
    private String path;

    public Listener(List<String> whiteList, String path) {
        this.whiteList = whiteList;
        this.path = path;
    }

    @KafkaListener(topics = {"${kafka.consumer.topic}"})
    public void listen(ConsumerRecord<byte[], byte[]> record) {
        try {
            Map<String, String> allFields = new HashMap<>();
            Map<Descriptors.FieldDescriptor, Object> allFields1 = AlarmProtoBuf.SeclogMain.parseFrom(record.value()).getAllFields();
            allFields1.forEach((k1, v1) -> {
                String name1 = k1.getName().toLowerCase();
                String value1 = v1.toString();
                if (name1.equals("eventstarttime")) {
                    value1 = DateFormatUtils.format(new Date(Long.valueOf(value1)), "yyyyMMddHHmmss");
                }
                allFields.put(name1, value1);

            });
            String deviceaddress = allFields.get("deviceaddress");
            if(whiteList.contains(deviceaddress)){
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File f;
                if (path.endsWith("/")) {
                    f = new File(path + deviceaddress);
                } else {
                    f = new File(path + "/" + deviceaddress);
                }
                logger.debug("kafka的value: " + allFields);
                try {
                    FileUtils.writeStringToFile(f, JsonUtils.objectToJson(allFields) + "\r\n", "UTF-8", true);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (InvalidProtocolBufferException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }


    }
}