package cn.qx.sys.service;


import java.util.List;

import cn.qx.sys.entity.Tags;

/**
 * 
 * @author Satone
 * @date 2019年2月20日下午9:44:30
 */
public interface TagsService extends BaseService<Tags> {

    Tags findByName(String name);

//    List<Tags> findByArticleTagsId(long articleId, long tagsId);

    /**
     * 根据文章ID查询其关联的标签数据
     *
     * @param id
     * @return
     */
    List<Tags> findByArticleId(long id);
}
