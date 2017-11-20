package cn.lsmsp.bigdata.check.policy.pojo;

import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Map;

public class Ruler {
    private String condition;
    private Map<String,String> content;
    private Integer rulerId;

    public Integer getRulerId() {
        return rulerId;
    }

    public void setRulerId(Integer rulerId) {
        this.rulerId = rulerId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Map<String, String> getContent() {
        return content;
    }

    public void setContent(Map<String, String> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Ruler{" +
                "condition='" + condition + '\'' +
                ", content=" + content +
                ", rulerId=" + rulerId +
                '}';
    }
}
