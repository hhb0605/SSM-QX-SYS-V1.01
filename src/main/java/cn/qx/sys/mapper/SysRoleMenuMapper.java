package cn.qx.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 此DAO对象对应sys_role_menus表中数据操作
 * 此表中存储的是角色和菜单的关系数据
 * @author Administrator
 *
 */
public interface SysRoleMenuMapper {
	/**
	 * 
	 * @param menuId
	 * @return
	 */
	int deleteObjectsByMenuId(Integer menuId);
	
	int insertObject(
			@Param("roleId")Integer roleId,
			@Param("menuIds")Integer[] menuIds);

	int deleteObjectsByRoleId(Integer roleId);
	
	List<Integer> findMenuIdsByRoleIds(
			@Param("roleIds")Integer[] roleIds);

}
