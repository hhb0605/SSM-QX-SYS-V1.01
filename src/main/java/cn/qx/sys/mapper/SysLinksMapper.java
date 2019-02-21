package cn.qx.sys.mapper;

import java.util.List;

import com.github.pagehelper.Page;

import cn.qx.sys.entity.Links;

/**
 * @auther TyCoding
 * @date 2018/10/18
 */
public interface SysLinksMapper {

    List<Links> findAll();

    Page<Links> findByPage(Links links);

    Links findById(long id);

    void save(Links links);

    void update(Links links);

    void delete(long id);

    Long findAllCount();
}
