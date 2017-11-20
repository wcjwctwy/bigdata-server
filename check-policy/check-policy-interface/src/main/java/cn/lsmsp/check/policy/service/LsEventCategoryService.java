package cn.lsmsp.check.policy.service;

import cn.lsmsp.bigdata.check.policy.pojo.LsEventCategory;
import cn.lsmsp.common.pojo.EventCategoryHtml;

import java.util.List;
import java.util.Map;

public interface LsEventCategoryService {

    Integer getCategoryIdByName(String name);

    String getCategoryNameById(Integer id);

    List<EventCategoryHtml> getEventCategoryHtmls();

}
