package cn.qx.sys.service.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

import cn.qx.common.annotation.RequestCache;
import cn.qx.common.annotation.RequestLog;
import cn.qx.common.exception.ServiceException;
import cn.qx.common.util.PageUtils;
import cn.qx.common.vo.CheckBox;
import cn.qx.common.vo.PageObject;
import cn.qx.common.vo.RoleMenuVo;
import cn.qx.sys.entity.Role;
import cn.qx.sys.entity.User;
import cn.qx.sys.mapper.SysMenuMapper;
import cn.qx.sys.mapper.SysRoleMapper;
import cn.qx.sys.mapper.SysRoleMenuMapper;
import cn.qx.sys.mapper.SysUserMapper;
import cn.qx.sys.mapper.SysUserRoleMapper;
import cn.qx.sys.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private SysRoleMapper sysRoleDao;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuDao;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    
    @RequiresPermissions("sys:role")
    public PageObject<Role> findPageObjects(String name, Integer pageCurrent) {
        // 1.验证参数合法性
        if (pageCurrent == null || pageCurrent < 1) {
            throw new IllegalArgumentException("页码不正确");
        }
        // 2.基于条件查询总记录数
        Integer rowCount = sysRoleDao.getRowCount(name);
        if (rowCount == 0) {
            throw new ServiceException("要查询的记录可能不存在");
        }
        // 3.查询当前页记录数
        Integer pageSize = 3;
        Integer startIndex = (pageCurrent - 1) * pageSize;
        List<Role> records = sysRoleDao.findPageObjects(name, startIndex, pageSize);
        // 4.封装为VO对象
        return PageUtils.newPageObject(rowCount, pageSize, pageCurrent, records);
	}
	
	@RequestLog("保存角色")
	@Override
	@RequiresPermissions("sys:role")
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

@RequiresPermissions("sys:role")
    @Override
    public RoleMenuVo findObjectById(Integer id) {
        // 1.合法性验证
        if (id == null || id <= 0)
            throw new ServiceException("id的值不合法");
        // 2.执行查询
        RoleMenuVo result = sysRoleDao.findObjectById(id);
        // 3.验证结果并返回
        if (result == null)
            throw new ServiceException("此记录已经不存在");
        return result;
    }

	@RequestLog("更新角色信息")
	@Override
	@RequiresPermissions("sys:role")
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
	@RequestLog("删除角色")
	@Override
	@RequiresPermissions("sys:role")
	public int deleteObject(Integer id) {
		//1.参数合法性校验
		if(id==null||id<1)
		throw new IllegalArgumentException("id值不合法");
		//2.删除角色自身信息
		int rows=sysRoleDao.deleteObject(id);
		if(rows==0)
		throw new ServiceException("记录可能已经不存在");
		//3.删除角色菜单关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		//4.删除角色用户关系数据
		sysUserRoleMapper.deleteObjectsByRoleId(id);
		return rows;
	}

  @RequiresPermissions("sys:role")
    @Override
    public List<CheckBox> findObjects() {
        return sysRoleDao.findObjects();
    }

    @RequestCache
    @Override
    public List<String> findCurrentMenus(String username) {
        User user = sysUserMapper.findByName(username);
        // 查询用户的角色id
        List<Integer> roleIds = sysUserRoleMapper.findRoleIdsByUserId((int) user.getId());
        Integer[] rids = new Integer[roleIds.size()];
        roleIds.toArray(rids);
        // 查询角色的菜单id
        List<Integer> mids = sysRoleMenuDao.findMenuIdsByRoleIds(rids);
        Integer[] menuIds = new Integer[mids.size()];
        mids.toArray(menuIds);
        // 查询权限字符串
        List<String> permissions = sysMenuMapper.findPermissions(menuIds);
        return permissions;
    }
}
