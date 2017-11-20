package cn.lsmsp.bigdata.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EventCount implements Serializable{
    private Long count;
    private Map<String,EventCount> eventCounts = new HashMap<>();

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Map<String, EventCount> getEventCounts() {
        return eventCounts;
    }

    public void setEventCounts(Map<String, EventCount> eventCounts) {
        this.eventCounts = eventCounts;
    }
}
