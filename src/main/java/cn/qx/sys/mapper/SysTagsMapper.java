package cn.qx.sys.mapper;

import java.util.List;

import com.github.pagehelper.Page;

import cn.qx.sys.entity.Tags;

/**
 * 
 * @author Satone
 * @date 2019年2月21日
 */
public interface SysTagsMapper {

    List<Tags> findAll();

    Page<Tags> findByPage(Tags tags);

    Tags findById(long id);

    void save(Tags tags);

    void update(Tags tags);

    void delete(long id);

    boolean exists(String name);

    Tags findByName(String name);

//    List<Tags> findByArticleTagsId(long articleId, long tagsId);

    Long findAllCount();

    List<Tags> findByArticleId(long id);
}
