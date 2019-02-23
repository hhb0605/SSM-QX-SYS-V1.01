package cn.qx.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

import cn.qx.common.exception.ServiceException;
import cn.qx.common.vo.Node;
import cn.qx.sys.entity.Menu;
import cn.qx.sys.mapper.SysMenuMapper;
import cn.qx.sys.mapper.SysRoleMenuMapper;
import cn.qx.sys.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService{
	@Autowired
	private SysMenuMapper sysMenuDao;
	@Autowired
	private SysRoleMenuMapper sysRoleMenuDao;
	@Override
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> list = sysMenuDao.findObjects();
		if(list.size()==0||list==null) {
			throw new ServiceException("没有对应的记录");
		}
		// list.forEach(System.out::println);
		return list;
	}

	@Override
	public int deleteObject(Integer id) {
		//1.验证数据的合法性
		if(id==null||id<=0)
			throw new ServiceException("请先选择");
		//2.基于id进行子元素查询
		int count=sysMenuDao.getChildCount(id);
		if(count>0)
			throw new ServiceException("请先删除子菜单");
		//3.删除菜单元素
		int rows=sysMenuDao.deleteObject(id);
		if(rows==0)
			throw new ServiceException("此菜单可能已经不存在");
		//4.删除角色,菜单关系数据
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		//5.返回结果
		return rows;
	}
	
	@Override
	public List<Node> findZtreeMenuNodes() {
		List<Node> list = sysMenuDao.findZtreeMenuNodes();
		if(list==null||list.size()==0) {
			throw new ServiceException("没有对应记录");
		}
		return list;
	}

	@Override
	public int insertObject(Menu entity) {
        // 1.合法验证
		if (entity==null) {
			throw new ServiceException("保存对象不能为空");
		}
		if(StringUtils.isEmpty(entity.getName())) {
			throw new ServiceException("菜单名不能为空");
		}
		if(StringUtils.isEmpty(entity.getPermission())) {
			throw new ServiceException("权限标识不能为空");
		}
		int rows;
		// 2.保存数据
		try {
			rows = sysMenuDao.insertObject(entity);
		} catch (Exception e) {
			e.printStackTrace();
		    throw new ServiceException("系统维护中");
		}
		return rows;
	}

	@Override
	public int updateObject(Menu entity) {
		//1.合法验证
		if(entity==null)
			throw new ServiceException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new ServiceException("菜单名不能为空");
		if(StringUtils.isEmpty(entity.getPermission())) {
			throw new ServiceException("权限标识不能为空");
		}
		//2.更新数据
		int rows=sysMenuDao.updateObject(entity);
		if(rows==0)
			throw new ServiceException("记录可能已经不存在");
		//3.返回数据
		return rows;
	}

}
