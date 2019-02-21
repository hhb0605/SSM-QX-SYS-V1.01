package cn.qx.sys.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.qx.common.enums.ResultEnums;
import cn.qx.common.exception.ResultException;
import cn.qx.common.vo.ArticleArchives;
import cn.qx.common.vo.PageBean;
import cn.qx.sys.entity.Article;
import cn.qx.sys.entity.ArticleCategory;
import cn.qx.sys.entity.ArticleTags;
import cn.qx.sys.entity.Category;
import cn.qx.sys.entity.Tags;
import cn.qx.sys.mapper.SysArticleMapper;
import cn.qx.sys.service.ArticleCategoryService;
import cn.qx.sys.service.ArticleService;
import cn.qx.sys.service.ArticleTagsService;
import cn.qx.sys.service.CategoryService;
import cn.qx.sys.service.TagsService;

/**
 * @auther TyCoding
 * @date 2018/10/16
 */
@Service
@SuppressWarnings("all")
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private SysArticleMapper articleMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagsService tagsService;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private ArticleTagsService articleTagsService;

    @Override
    public Long findAllCount() {
        return articleMapper.findAllCount();
    }

    @Override
    public List<Article> findAll() {
        return articleMapper.findAll();
    }

    @Override
    public PageBean findByPage(Article article, int pageCode, int pageSize) {
        PageHelper.startPage(pageCode, pageSize);
        Page<Article> page = articleMapper.findByPage(article);
        List<Article> articleList = page.getResult();
        findInit(articleList);
        return new PageBean(page.getTotal(), articleList);
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
            try {
                article.setTags(new ObjectMapper().writeValueAsString(tagsList));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new ResultException(ResultEnums.INNER_ERROR);
            }
        }
    }

    @Override
    public PageBean findByPageForSite(Integer pageCode, Integer pageSize) {
        PageHelper.startPage(pageCode, pageSize);
        Page<Article> page = articleMapper.findByPageForSite();
        List<Article> articleList = page.getResult();
        findInit(articleList);
        return new PageBean(page.getTotal(), articleList);
    }

    @Override
    public Article findById(long id) {
        Article article = articleMapper.findById(id);
        List<Article> articleList = new ArrayList<>();
        articleList.add(article);
        findInit(articleList);
        return article;
    }

    @Override
    public void save(Article article) {
        try {
            if (article.getState() == "1") {
                //发布
                article.setPublishTime(new Date());
            }
            article.setEditTime(new Date());
            articleMapper.save(article);
            long articleId = articleMapper.getLastId(); //查询最新插入文章的ID
            updateArticleCategoryTags(article, articleId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultEnums.INNER_ERROR);
        }
    }

    /**
     * 更新文章-分类-标签，三表间的关联
     *
     * @param article
     * @param id
     */
    private void updateArticleCategoryTags(Article article, long id) {
        if (article.getCategory() != null) {
            //证明新插入的文章有分类信息，将这个文章分类保存到分类表中
            categoryService.save(new Category(article.getCategory()));

            //保存了分类信息再保存分类-文章的关联信息
            Category category = categoryService.findByName(article.getCategory());
            articleCategoryService.save(new ArticleCategory(id, category.getId()));
        }
        if (article.getTags() != null) {
            //证明新插入的文章有标签数据，将标签数据保存到标签表中
            List<String> list ; //前端传来的标签是JSON字符串格式的标签名称
            try {
                list =new ObjectMapper().readValue(article.getTags(),List.class);
            } catch (IOException e) {
                e.printStackTrace();
                throw new ResultException(ResultEnums.INNER_ERROR);
            }
            if (list.size() > 0) {
                for (String name : list) {
                    tagsService.save(new Tags(name));
                    Tags tags = tagsService.findByName(name); //因为标签是多个的，需要依次将标签信息保存到标签表中

                    if (tags != null) {
                        //说明该标签插入成功或已存在，建立标签-文章关联信息
                        articleTagsService.save(new ArticleTags(id, tags.getId()));
                    }
                }
            }
        }
    }

    @Override
    public void update(Article article) {
        try {
            articleMapper.update(article);
            updateArticleCategoryTags(article, article.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultEnums.INNER_ERROR);
        }
    }

    @Override
    public void delete(Long... ids) {
        try {
            for (long id : ids) {
                articleMapper.delete(id);
                //删除文章-分类表的关联
                articleCategoryService.deleteByArticleId(id);
                //删除文章-标签表的关联
                articleTagsService.deleteByArticleId(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultEnums.INNER_ERROR);
        }
    }

    @Override
    public List<Article> findByCategory(String category) {
        return articleMapper.findByCategory(category);
    }

    @Override
    public List<ArticleArchives> findArchives() {
        List<ArticleArchives> articleArchivesList = new ArrayList<ArticleArchives>();
        try {
            List<String> dates = articleMapper.findArchivesDates();
            for (String date : dates) {
                List<Article> articleList = articleMapper.findArchivesByDate(date);
                ArticleArchives articleArchives = new ArticleArchives(date, articleList);
                articleArchivesList.add(articleArchives);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultEnums.INNER_ERROR);
        }
        return articleArchivesList;
    }

    @Override
    public List<Article> findFuzzyByTitle(String title) {
        try {
            return articleMapper.findFuzzyByTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultEnums.INNER_ERROR);
        }
    }

    @Override
    public void addEyeCount(long id) {
        try {
            articleMapper.addEyeCount(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(ResultEnums.INNER_ERROR);
        }
    }
}
