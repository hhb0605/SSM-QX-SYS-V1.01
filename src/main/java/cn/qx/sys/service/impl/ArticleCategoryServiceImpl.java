package cn.qx.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.qx.common.exception.ServiceException;
import cn.qx.common.vo.PageBean;
import cn.qx.sys.entity.ArticleCategory;
import cn.qx.sys.mapper.ArticleCategoryMapper;
import cn.qx.sys.service.ArticleCategoryService;

/**
 * 
 * @author Satone
 * @date 2019年2月21日
 */
@Service
@Transactional
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    public Long findAllCount() {
        return null;
    }

    @Override
    public List<ArticleCategory> findAll() {
        return null;
    }

    @Override
    public PageBean<ArticleCategory> findByPage(ArticleCategory articleCategory, int pageCode, int pageSize) {
        return null;
    }

    @Override
    public ArticleCategory findById(long id) {
        return null;
    }

    @Override
    public void save(ArticleCategory articleCategory) {
        try {
            if (!exists(articleCategory)) {
                articleCategoryMapper.save(articleCategory);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    private boolean exists(ArticleCategory articleCategory) {
        return articleCategoryMapper.exists(articleCategory.getArticleId(), articleCategory.getCategoryId());
    }

    @Override
    public void update(ArticleCategory articleCategory) {
    }

    @Override
    public void delete(Long... ids) {
    }

    @Override
    public void deleteByArticleId(long id) {
        try {
            if (exists(new ArticleCategory(id, 0))) {
                articleCategoryMapper.deleteByArticleId(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByCategoryId(long id) {
        try {
            if (exists(new ArticleCategory(0, id))) {
                articleCategoryMapper.deleteByCategoryId(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }
}
