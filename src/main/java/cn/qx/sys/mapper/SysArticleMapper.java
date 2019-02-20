package cn.qx.sys.mapper;

import java.util.List;
import com.github.pagehelper.Page;

import cn.qx.sys.entity.Article;

/**
 * 
 * @author Satone
 * @date 2019年2月20日下午9:37:23
 */
public interface SysArticleMapper {

    List<Article> findAll();

    Page<Article> findByPage(Article article);

    Page<Article> findByPageForSite();

    Article findById(long id);

    void save(Article article);

    void update(Article article);

    void delete(long id);

    long getLastId();

    List<Article> findByCategory(String category);

    Long findAllCount();

    List<String> findArchivesDates();

    List<Article> findArchivesByDate(String date);

    List<Article> findFuzzyByTitle(String title);

    void addEyeCount(long id);
}
