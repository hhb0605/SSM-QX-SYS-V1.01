package cn.qx.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.qx.common.exception.ServiceException;
import cn.qx.common.vo.PageBean;
import cn.qx.sys.entity.Tags;
import cn.qx.sys.mapper.SysTagsMapper;
import cn.qx.sys.service.ArticleTagsService;
import cn.qx.sys.service.TagsService;

/**
 * 
 * @author Satone
 * @date 2019年2月21日
 */
@Service
@Transactional
public class TagsServiceImpl implements TagsService {

    @Autowired
    private SysTagsMapper tagsMapper;

    @Autowired
    private ArticleTagsService articleTagsService;

    @Override
    public Long findAllCount() {
        return tagsMapper.findAllCount();
    }

    @Override
    public List<Tags> findAll() {
        return tagsMapper.findAll();
    }

    @Override
    public PageBean<Tags> findByPage(Tags tags, int pageCode, int pageSize) {
        PageHelper.startPage(pageCode, pageSize);
        Page<Tags> page = tagsMapper.findByPage(tags);
        return new PageBean<Tags>(page.getTotal(), page.getResult());
    }

    @Override
    public Tags findById(long id) {
        return tagsMapper.findById(id);
    }

    @Override
    public void save(Tags tags) {
        try {
            if (!exists(tags)) {
                tagsMapper.save(tags);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    private boolean exists(Tags tags) {
        return tagsMapper.exists(tags.getName());
    }

    @Override
    public void update(Tags tags) {
        try {
            if (tags.getId() != 0) {
                tagsMapper.update(tags);
            } else {
                throw new ServiceException("标签不能为空");
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Long... ids) {
        try {
            if (ids != null && ids.length > 0) {
                for (long id : ids) {
                    tagsMapper.delete(id);

                    // 删除该标签与文章有关联的关联信息
                    articleTagsService.deleteByTagsId(id);
                }
            } else {
                throw new ServiceException("id不能为空");
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Tags findByName(String name) {
        return tagsMapper.findByName(name);
    }

    @Override
    public List<Tags> findByArticleId(long id) {
        return tagsMapper.findByArticleId(id);
    }

    /*@Override
    public List<Tags> findByArticleTagsId(long articleId, long tagsId) {
        return tagsMapper.findByArticleTagsId(articleId, tagsId);
    }*/
}
