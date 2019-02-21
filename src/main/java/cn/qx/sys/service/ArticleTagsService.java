package cn.qx.sys.service;


import java.util.List;

import cn.qx.sys.entity.ArticleTags;
import cn.qx.sys.entity.Tags;

/**
 * @auther TyCoding
 * @date 2018/10/22
 */
public interface ArticleTagsService extends BaseService<ArticleTags> {

    List<Tags> findByArticleId(long articleId);

    /**
     * 根据文章ID删除
     * @param id
     */
    void deleteByArticleId(long id);

    /**
     * 根据标签ID删除
     * @param id
     */
    void deleteByTagsId(long id);
}
