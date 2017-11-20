package cn.lsmsp.bigdata.check.policy.pojo;

import java.util.List;

public class XmlRuler {
    private Integer pluginCode;
    private List<TranslationRuler> translation;
    private List<Ruler> rulers;

    public Integer getPluginCode() {
        return pluginCode;
    }

    public void setPluginCode(Integer pluginCode) {
        this.pluginCode = pluginCode;
    }

    public List<TranslationRuler> getTranslation() {
        return translation;
    }

    public void setTranslation(List<TranslationRuler> translation) {
        this.translation = translation;
    }

    public List<Ruler> getRulers() {
        return rulers;
    }

    public void setRulers(List<Ruler> rulers) {
        this.rulers = rulers;
    }

    @Override
    public String toString() {
        return "RulerConfig{" +
                "pluginCode=" + pluginCode +
                ", translation=" + translation +
                ", rulers=" + rulers +
                '}';
    }
}
