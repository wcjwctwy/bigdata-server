package cn.lsmsp.check.policy.service.impl.category;

import cn.lsmsp.bigdata.check.policy.dao.LsEventSubcategoryDao;
import cn.lsmsp.bigdata.check.policy.pojo.LsEventSubcategory;
import cn.lsmsp.check.policy.service.LsEventSubcategoryService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LsEventSubcategoryServiceImpl implements LsEventSubcategoryService {

    @Autowired
    private LsEventSubcategoryDao lsEventSubcategoryDao;


    @Override
    public Integer getSubcategoryIdByName(String name) {
        LsEventSubcategory eventSubcategoryByName = lsEventSubcategoryDao.getEventSubcategoryByName(name);
        if(eventSubcategoryByName!=null){
            return eventSubcategoryByName.getId();
        }
        return null;
    }

    @Override
    public Integer getEventSubcategoryByNameAndCategoryId(String name, Integer categoryId) {
        LsEventSubcategory eventSubcategoryByName = lsEventSubcategoryDao.getEventSubcategoryByNameAndCategoryId(name,categoryId);
        if(eventSubcategoryByName!=null){
            return eventSubcategoryByName.getId();
        }
        return null;
    }

    @Override
    public Integer getSubCountByCategoryId(Integer categoryId) {
        Integer subCountByCateoryId = lsEventSubcategoryDao.getSubCountByCateoryId(categoryId);
        if(subCountByCateoryId==null){
            return 0;
        }
        return subCountByCateoryId;
    }

    @Override
    public String getSubcategoryNameById(Integer id) {
        LsEventSubcategory eventSubcategoryById = lsEventSubcategoryDao.getEventSubcategoryById(id);
        if(eventSubcategoryById!=null){
            return eventSubcategoryById.getName();
        }
        return null;
    }

    @Override
    public List<LsEventSubcategory> getEventSubcategoriesByCategoryId(Integer categoryId,Integer page,Integer rows) {
        PageHelper.startPage(page,rows);
        List<LsEventSubcategory> eventSubcategoriesByCategoryId = lsEventSubcategoryDao.getEventSubcategoriesByCategoryId(categoryId);
        return eventSubcategoriesByCategoryId;
    }
}
