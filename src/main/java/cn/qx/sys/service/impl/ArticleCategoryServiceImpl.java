package cn.qx.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.qx.common.enums.ResultEnums;
import cn.qx.common.exception.ResultException;
import cn.qx.common.vo.PageBean;
import cn.qx.sys.entity.ArticleCategory;
import cn.qx.sys.mapper.SysArticleCategoryMapper;
import cn.qx.sys.service.ArticleCategoryService;

/**
 * @auther TyCoding
 * @date 2018/10/22
 */
@Service
@Transactional
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    @Autowired
    private SysArticleCategoryMapper articleCategoryMapper;

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
            throw new ResultException(ResultEnums.INNER_ERROR);
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
            throw new ResultException(ResultEnums.INNER_ERROR);
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
            throw new ResultException(ResultEnums.INNER_ERROR);
        }
    }
}
