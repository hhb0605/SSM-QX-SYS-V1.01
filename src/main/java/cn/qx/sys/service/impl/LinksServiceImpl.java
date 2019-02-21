package cn.qx.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.qx.common.exception.ServiceException;
import cn.qx.common.vo.PageBean;
import cn.qx.sys.entity.Links;
import cn.qx.sys.mapper.SysLinksMapper;
import cn.qx.sys.service.LinksService;

/**
 * @auther TyCoding
 * @date 2018/10/19
 */
@Service
@SuppressWarnings("all")
@Transactional
public class LinksServiceImpl implements LinksService {

    @Autowired
    private SysLinksMapper linksMapper;

    @Override
    public Long findAllCount() {
        return linksMapper.findAllCount();
    }

    @Override
    public List<Links> findAll() {
        return linksMapper.findAll();
    }

    @Override
    public PageBean findByPage(Links links, int pageCode, int pageSize) {
        PageHelper.startPage(pageCode, pageSize);
        Page page = linksMapper.findByPage(links);
        return new PageBean(page.getTotal(), page.getResult());
    }

    @Override
    public Links findById(long id) {
        return linksMapper.findById(id);
    }

    @Override
    public void save(Links links) {
        try {
            linksMapper.save(links);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Links links) {
        try {
            linksMapper.update(links);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Long... ids) {
        try {
            for (long id : ids) {
                linksMapper.delete(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }
}
