package cn.qx.sys.mapper;

import java.util.List;

import com.github.pagehelper.Page;

import cn.qx.sys.entity.Category;

/**
 * 
 * @author Satone
 * @date 2019年2月20日
 */
public interface SysCategoryMapper {

    List<Category> findAll();

    Page<Category> findByPage(Category category);

    Category findById(long id);

    void save(Category category);

    void update(Category category);

    void delete(long id);

    boolean exists(String name);

    Category findByName(String name);

    List<Category> findCategoryByArticleId(long id);
}
