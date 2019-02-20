package cn.qx.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.qx.common.exception.ServiceException;
import cn.qx.common.vo.ArticleArchives;
import cn.qx.common.vo.PageBean;
import cn.qx.sys.entity.Article;
import cn.qx.sys.entity.Category;
import cn.qx.sys.entity.Tags;
import cn.qx.sys.mapper.SysArticleMapper;
import cn.qx.sys.service.ArticleService;
import cn.qx.sys.service.CategoryService;
import cn.qx.sys.service.TagsService;

/**
 * 
 * @author Satone
 * @date 2019年2月20日下午9:36:14
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private SysArticleMapper articleMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagsService tagsService;

    @Override
    public List<Article> findAll() {
        return articleMapper.findAll();
    }

    /**
     * 使用PageHelper进行分页处理
     */
    @Override
    public PageBean<Article> findByPage(Article article, int pageCode, int pageSize) {
        PageHelper.startPage(pageCode, pageSize);
        Page<Article> page = articleMapper.findByPage(article);
        List<Article> articleList = page.getResult();
        findInit(articleList);
        return new PageBean<Article>(page.getTotal(), articleList);
    }

    @Override
    public PageBean<Article> findByPageForSite(Integer pageCode, Integer pageSize) {
        PageHelper.startPage(pageCode, pageSize);
        Page<Article> page = articleMapper.findByPageForSite();
        List<Article> articleList = page.getResult();
        findInit(articleList);
        return new PageBean<Article>(page.getTotal(), articleList);
    }

    @Override
    public Article findById(long id) {
        Article article = articleMapper.findById(id);
        List<Article> articleList = new ArrayList<>();
        articleList.add(article);
        findInit(articleList);
        return article;
    }

    private void findInit(List<Article> list){
        for (Article article : list) {
            List<Category> categoryList = categoryService.findByArticleId(article.getId());
            if (categoryList.size() > 0) {
                article.setCategory(categoryList.get(0).getName());
            }
            List<Tags> tagsList = tagsService.findByArticleId(article.getId());
            List<String> stringList = new ArrayList<>();
            for (Tags tags : tagsList) {
                stringList.add(tags.getName());
            }
            // 包装JSON转换异常
            try {
                article.setTags(new ObjectMapper().writeValueAsString(tagsList));
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public Long findAllCount() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(Article t) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(Article t) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(Long... ids) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Article> findByCategory(String category) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ArticleArchives> findArchives() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Article> findFuzzyByTitle(String title) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addEyeCount(long id) {
        // TODO Auto-generated method stub
        
    }

}
