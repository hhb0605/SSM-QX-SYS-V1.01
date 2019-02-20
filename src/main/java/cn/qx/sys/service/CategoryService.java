package cn.qx.sys.service;


import java.util.List;

import cn.qx.sys.entity.Category;

/**
 * 
 * @author Satone
 * @date 2019年2月20日下午9:44:20
 */
public interface CategoryService extends BaseService<Category> {

    Category findByName(String name);

    /**
     * 根据文章ID查询其关联的分类数据
     *
     * @param id
     * @return
     */
    List<Category> findByArticleId(long id);
}
