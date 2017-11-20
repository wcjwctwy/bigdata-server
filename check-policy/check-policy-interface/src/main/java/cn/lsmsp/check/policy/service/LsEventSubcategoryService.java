package cn.lsmsp.check.policy.service;

import cn.lsmsp.bigdata.check.policy.pojo.LsEventSubcategory;

import java.util.List;

public interface LsEventSubcategoryService {
    Integer getSubcategoryIdByName(String name);
    Integer getEventSubcategoryByNameAndCategoryId(String name,Integer categoryId);
    Integer getSubCountByCategoryId(Integer categoryId);
    String getSubcategoryNameById(Integer id);
    List<LsEventSubcategory> getEventSubcategoriesByCategoryId(Integer categoryId,Integer page,Integer rows);
}
