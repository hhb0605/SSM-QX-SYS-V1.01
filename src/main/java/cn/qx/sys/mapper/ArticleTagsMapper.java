package cn.qx.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.qx.sys.entity.ArticleTags;
import cn.qx.sys.entity.Tags;

/**
 * 
 * @author Satone
 * @date 2019年2月21日
 */
public interface ArticleTagsMapper {

    void save(ArticleTags articleTags);

    boolean exists(@Param("articleId") long articleId, @Param("tagsId") long tagsId);

    List<Tags> findByArticleId(long articleId);

    void deleteByArticleId(long id);

    void deleteByTagsId(long id);
}
