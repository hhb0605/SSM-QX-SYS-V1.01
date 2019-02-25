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
    /**
	 * 基于用户id查询用户所属角色
	 * @param id
	 * @return
	 */
    List<Integer> findRoleIdsByUserId(Integer id);
    /**
	 * 基于用户id删除用户和角色关系数据
	 * @param userId
	 * @return
	 */
    int deleteObjectsByUserId(Integer userId);
    /**
	 * 基于角色id删除用户和角色的关系
	 * @param roleId
	 * @return
	 */
	int deleteObjectsByRoleId(Integer roleId);
}
