package cn.lsmsp.bigdata.check.policy.pojo;

public class TranslationRuler {
    private String key;
    private String value;
    private String eventName;
    private String eventCategory;
    private String eventCategoryTechnique;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEventCategoryTechnique() {
        return eventCategoryTechnique;
    }

    public void setEventCategoryTechnique(String eventCategoryTechnique) {
        this.eventCategoryTechnique = eventCategoryTechnique;
    }

    @Override
    public String toString() {
        return "TranslationRuler{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventCategory='" + eventCategory + '\'' +
                ", eventCategoryTechnique='" + eventCategoryTechnique + '\'' +
                '}';
    }
}
