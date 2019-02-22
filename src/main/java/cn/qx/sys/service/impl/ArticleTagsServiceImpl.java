package cn.qx.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.qx.common.exception.ServiceException;
import cn.qx.common.vo.PageBean;
import cn.qx.sys.entity.ArticleTags;
import cn.qx.sys.entity.Tags;
import cn.qx.sys.mapper.SysArticleTagsMapper;
import cn.qx.sys.service.ArticleTagsService;

/**
 * 
 * @author Satone
 * @date 2019年2月21日
 */
@Service
@SuppressWarnings("all")
@Transactional
public class ArticleTagsServiceImpl implements ArticleTagsService {

    @Autowired
    private SysArticleTagsMapper articleTagsMapper;

    @Override
    public Long findAllCount() {
        return null;
    }

    @Override
    public List<ArticleTags> findAll() {
        return null;
    }

    @Override
    public PageBean findByPage(ArticleTags articleTags, int pageCode, int pageSize) {
        return null;
    }

    @Override
    public ArticleTags findById(long id) {
        return null;
    }

    @Override
    public void save(ArticleTags articleTags) {
        try {
            if (!exists(articleTags)) {
                articleTagsMapper.save(articleTags);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    private boolean exists(ArticleTags articleTags) {
        return articleTagsMapper.exists(articleTags.getArticleId(), articleTags.getTagsId());
    }

    @Override
    public void update(ArticleTags articleTags) {

    }

    @Override
    public void delete(Long... ids) {

    }

    @Override
    public List<Tags> findByArticleId(long articleId) {
        return articleTagsMapper.findByArticleId(articleId);
    }

    @Override
    public void deleteByArticleId(long id) {
        try {
            if (exists(new ArticleTags(id, 0))) {
                articleTagsMapper.deleteByArticleId(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByTagsId(long id) {
        try {
            if (exists(new ArticleTags(0, id))) {
                articleTagsMapper.deleteByTagsId(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }
}
