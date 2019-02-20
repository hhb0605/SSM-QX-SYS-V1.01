package cn.qx.sys.mapper;

import cn.qx.sys.entity.SysLinks;

public interface SysLinksMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLinks record);

    int insertSelective(SysLinks record);

    SysLinks selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLinks record);

    int updateByPrimaryKey(SysLinks record);
}