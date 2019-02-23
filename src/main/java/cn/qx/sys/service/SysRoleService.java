package cn.qx.sys.service;

import java.util.List;

import cn.qx.common.vo.CheckBox;
import cn.qx.common.vo.PageObject;
import cn.qx.common.vo.RoleMenuVo;
import cn.qx.sys.entity.Role;

public interface SysRoleService {
	// 根据条件查询数据
	PageObject<Role> findPageObjects(
			String name, 
			Integer pageCurrent);
	
	// 插入数据的方法
	int saveObject(Role entity,Integer[] menuIds);
	
	// 基于参数数据查询角色信息
	RoleMenuVo findObjectById(Integer id);
	
	int updateObject(Role entity,Integer[] menuIds);
	
    public List<CheckBox> findObjects();

}
