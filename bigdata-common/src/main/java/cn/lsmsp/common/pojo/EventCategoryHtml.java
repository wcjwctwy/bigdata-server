package cn.lsmsp.common.pojo;

public class EventCategoryHtml {

    private Integer id;
    private String name;
    private String zhName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    @Override
    public String toString() {
        return "EventCategoryHtml{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", zhName='" + zhName + '\'' +
                '}';
    }
}
