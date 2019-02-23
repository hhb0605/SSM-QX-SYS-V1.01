package cn.qx.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.qx.common.vo.CheckBox;
import cn.qx.common.vo.RoleMenuVo;
import cn.qx.sys.entity.Role;


public interface SysRoleMapper {
	/**
	 * 基于角色名获取记录总数
	 * @param name
	 * @return
	 */
    int getRowCount(String name);
    
    /**
     * 查询当前页要呈现的角色信息
     * @param name 名字
     * @param startIndex 开始下标
     * @param pageSize 页码记录数目
     * @return
     */
    List<Role> findPageObjects(
    	@Param("name")String name, 
    	@Param("startIndex")Integer startIndex, 
    	@Param("pageSize")Integer pageSize);
    
    int insertObject(Role entity);
    
    RoleMenuVo findObjectById(Integer id);
    
    int updateObject(Role entity);
    
    List<CheckBox> findObjects();
    
    List<Integer> findRoleIdsByUserId(
			Integer id);

}
