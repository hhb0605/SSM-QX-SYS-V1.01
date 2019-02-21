package cn.qx.sys.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.github.pagehelper.Page;
import cn.qx.sys.entity.Comments;
/**
 * 
 * @author Satone
 * @date 2019年2月21日
 */
public interface SysCommentsMapper {

    List<Comments> findAll();

    Page<Comments> findByPage(Comments comments);

    /**
     * 分页查询指定文章的评论数据
     *
     * @param articleId
     * @param sort
     * @return
     */
    Page<Comments> findCommentsList(@Param("articleId") int articleId, @Param("sort") int sort);

    /**
     * 查询所有评论数据，用于从中筛选实现分页
     *
     * @param articleId
     * @param sort
     * @return
     */
    Page<Comments> findAllId(@Param("articleId") int articleId, @Param("sort") int sort);

    Comments findById(long id);

    void save(Comments comments);

    void update(Comments comments);

    void delete(long id);

    Long findAllCount();

    Long findCountByArticleId(long articleId);

}
