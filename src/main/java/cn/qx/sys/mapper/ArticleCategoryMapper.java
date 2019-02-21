package cn.qx.sys.mapper;

import org.apache.ibatis.annotations.Param;

import cn.qx.sys.entity.ArticleCategory;

/**
 * 
 * @author Satone
 * @date 2019年2月21日
 */
public interface ArticleCategoryMapper {

    void save(ArticleCategory articleCategory);

    boolean exists(@Param("articleId") long articleId, @Param("categoryId") long categoryId);

    void deleteByArticleId(long id);

    void deleteByCategoryId(long id);
}
