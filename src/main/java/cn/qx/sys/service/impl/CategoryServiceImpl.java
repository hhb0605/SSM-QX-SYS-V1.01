package cn.qx.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.qx.common.exception.ServiceException;
import cn.qx.common.vo.PageBean;
import cn.qx.sys.entity.Category;
import cn.qx.sys.mapper.SysCategoryMapper;
import cn.qx.sys.service.ArticleCategoryService;
import cn.qx.sys.service.ArticleService;
import cn.qx.sys.service.CategoryService;

/**
 * @auther TyCoding
 * @date 2018/10/18
 */
@Service
@SuppressWarnings("all")
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private SysCategoryMapper categoryMapper;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Override
    public Long findAllCount() {
        return null;
    }

    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    @Override
    public PageBean findByPage(Category category, int pageCode, int pageSize) {
        PageHelper.startPage(pageCode, pageSize);
        Page page = categoryMapper.findByPage(category);
        return new PageBean(page.getTotal(), page.getResult());
    }

    @Override
    public Category findById(long id) {
        return categoryMapper.findById(id);
    }

    @Override
    public void save(Category category) {
        try {
            if (!exists(category)) {
                categoryMapper.save(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 判断添加的标签是否已存在
     *
     * @param category
     * @return
     */
    private boolean exists(Category category) {
        return categoryMapper.exists(category.getName());
    }

    @Override
    public void update(Category category) {
        try {
            if (category.getId() != 0) {
                categoryMapper.update(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    /**
     * 删除分类
     *
     * @param ids
     */
    @Override
    public void delete(Long... ids) {
        try {
            for (long id : ids) {
                categoryMapper.delete(id);

                // 删除与该分类与文章关联的信息
                articleCategoryService.deleteByCategoryId(id);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Category findByName(String name) {
        return categoryMapper.findByName(name);
    }

    @Override
    public List<Category> findByArticleId(long id) {
        return categoryMapper.findCategoryByArticleId(id);
    }
}
