package cn.qx.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

import cn.qx.common.exception.ServiceException;
import cn.qx.common.utils.PageUtils;
import cn.qx.common.vo.CheckBox;
import cn.qx.common.vo.PageObject;
import cn.qx.common.vo.RoleMenuVo;
import cn.qx.sys.entity.Role;
import cn.qx.sys.mapper.SysRoleMapper;
import cn.qx.sys.mapper.SysRoleMenuMapper;
import cn.qx.sys.service.SysRoleService;

@Service
public class RoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleMapper sysRoleDao;
	@Autowired
	private SysRoleMenuMapper sysRoleMenuDao;

	public PageObject<Role> findPageObjects(String name, Integer pageCurrent) {
		// 1.验证参数合法性
		if (pageCurrent==null || pageCurrent<1) {
			throw new IllegalArgumentException("页码不正确");
		}
		// 2.基于条件查询总记录数
		Integer rowCount = sysRoleDao.getRowCount(name);
		if (rowCount==0) {
			throw new ServiceException("要查询的记录可能不存在");
		}
		// 3.查询当前页记录数
		Integer pageSize=3;
		Integer startIndex=(pageCurrent-1)*pageSize;
		List<Role> records = sysRoleDao.findPageObjects(name, startIndex, pageSize);
		// 4.封装为VO对象
//		PageObject<SysRole> po = new PageObject<>();
		return PageUtils.newPageObject(rowCount, pageSize, pageCurrent, records);
//		return po;
	}

	@Override
	public int saveObject(Role entity, Integer[] menuIds) {
		//1.合法性验证
    	if(entity==null)
    throw new ServiceException("保存数据不能为空");
    	if(StringUtils.isEmpty(entity.getName()))
    	throw new ServiceException("角色名不能为空");
   	if(menuIds==null||menuIds.length==0)
    	throw new ServiceException("必须为角色赋予权限");
    	//2.保存数据
    	int rows=sysRoleDao.insertObject(entity);
    	sysRoleMenuDao.insertObject(
    			entity.getId(),menuIds);
    	//3.返回结果
    	return rows;

	}

	@Override
    public RoleMenuVo findObjectById(Integer id) {
    	//1.合法性验证
    	if(id==null||id<=0)
    	throw new ServiceException("id的值不合法");
    	//2.执行查询
    	RoleMenuVo result = sysRoleDao.findObjectById(id);
  	//3.验证结果并返回
    	if(result==null)
    	throw new ServiceException("此记录已经不存在");
    	return result;
    }

	
	@Override
	public int updateObject(Role entity,Integer[] menuIds) {
		//1.合法性验证
		if(entity==null)
			throw new ServiceException("更新的对象不能为空");
		if(entity.getId()==null)
			throw new ServiceException("id的值不能为空");

		if(StringUtils.isEmpty(entity.getName()))
			throw new ServiceException("角色名不能为空");
		if(menuIds==null||menuIds.length==0)
			throw new ServiceException("必须为角色指定一个权限");

		//2.更新数据
		int rows=sysRoleDao.updateObject(entity);
		if(rows==0)
			throw new ServiceException("对象可能已经不存在");
		sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
		sysRoleMenuDao.insertObject(entity.getId(),menuIds);

		//3.返回结果
		return rows;
	}

	@Override
    public List<CheckBox> findObjects() {
     	return sysRoleDao.findObjects();
    }
}
