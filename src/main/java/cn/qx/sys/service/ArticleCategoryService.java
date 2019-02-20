package cn.qx.sys.service;

import cn.qx.sys.entity.ArticleCategory;

/**
 * 
 * @author Satone
 * @date 2019年2月20日
 */
public interface ArticleCategoryService extends BaseService<ArticleCategory> {

    /**
     * 根据文章ID删除
     * @param id
     */
    void deleteByArticleId(long id);

    /**
     * 根据分类ID删除
     * @param id
     */
    void deleteByCategoryId(long id);
}
