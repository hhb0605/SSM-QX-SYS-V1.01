package cn.qx.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysUserRoleMapper {
    /**
     * 负责将用户与角色的关系数据写入到数据库
     * 
     * @param userId  用户id
     * @param roleIds 多个角色id
     * @return
     */
    int insertObject(@Param("userId") Integer userId, @Param("roleIds") Integer[] roleIds);

    List<Integer> findRoleIdsByUserId(Integer id);

    int deleteObjectsByUserId(Integer userId);
}
