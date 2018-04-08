package cn.lsmsp.check.policy.service.impl.category;

import cn.lsmsp.bigdata.check.policy.dao.LsEventCategoryDao;
import cn.lsmsp.bigdata.check.policy.pojo.LsEventCategory;
import cn.lsmsp.check.policy.service.LsEventCategoryService;
import cn.lsmsp.common.pojo.EventCategoryHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LsEventCategoryServiceImpl implements LsEventCategoryService {

    @Autowired
    private LsEventCategoryDao lsEventCategoryDao;

    @Override
    public Integer getCategoryIdByName(String name) {
        LsEventCategory eventCategoryByName = lsEventCategoryDao.getEventCategoryByName(name);
        if(eventCategoryByName!=null){
            return eventCategoryByName.getId();
        }
        return null;
    }

    @Override
    public String getCategoryNameById(Integer id) {
        LsEventCategory eventCategoryById = lsEventCategoryDao.getEventCategoryById(id);
        if(eventCategoryById!=null){
            return eventCategoryById.getName();
        }
        return null;
    }

    @Override
    public EventCategoryHtml saveEventCategoryHtml(EventCategoryHtml eventCategoryHtml) {

        return null;
    }

    @Override
    public List<EventCategoryHtml> getEventCategoryHtmls() {
        List<LsEventCategory> eventCategories = lsEventCategoryDao.getEventCategories();
        List<EventCategoryHtml> result = new ArrayList<>();
        eventCategories.forEach(ec->{
            EventCategoryHtml eventCategoryHtml = new EventCategoryHtml();
            eventCategoryHtml.setId(ec.getId());
            eventCategoryHtml.setName(ec.getName());
            eventCategoryHtml.setZhName(ec.getZhName());
            result.add(eventCategoryHtml);
        });
        return result;
    }
}
