package cn.qx.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.qx.common.annotation.RequestCache;
import cn.qx.common.annotation.RequestLog;
import cn.qx.common.enums.ResultEnums;
import cn.qx.common.exception.ResultException;
import cn.qx.common.exception.ServiceException;
import cn.qx.common.util.PageUtils;
import cn.qx.common.util.ShiroUtils;
import cn.qx.common.vo.PageBean;
import cn.qx.common.vo.PageObject;
import cn.qx.common.vo.PasswordHelper;
import cn.qx.sys.entity.User;
import cn.qx.sys.mapper.SysUserMapper;
import cn.qx.sys.mapper.SysUserRoleMapper;
import cn.qx.sys.service.UserService;

/**
 * @auther TyCoding
 * @date 2018/10/18
 */
@Service
@SuppressWarnings("all")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private PasswordHelper passwordHelper;
    
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    @RequiresPermissions("sys:user")
    public Long findAllCount() {
        return null;
    }

    @Override
    @RequiresPermissions("sys:user")
    public List<User> findAll() {
        return null;
    }

    @Override
    @RequiresPermissions("sys:user")
    public PageBean findByPage(User user, int pageCode, int pageSize) {
        PageHelper.startPage(pageCode, pageSize);
        Page page = userMapper.findByPage(user);
        return new PageBean(page.getTotal(), page.getResult());
    }

    @Override
    @RequiresPermissions("sys:user")
    public User findById(long id) {
        return userMapper.findById(id);
    }
    @Override
    @RequiresPermissions("sys:user")
	public Map<String, Object> findObjectById(Long id) {
		//1.对参数进行校验
		if(id==null)
		throw new IllegalArgumentException("参数值无效");
		//2.查询用户信息
		User result=
				userMapper.findById(id);
		//3.查询用户对应的角色信息
		List<Integer> roleIds=
				sysUserRoleMapper.findRoleIdsByUserId(id.intValue());
		//4.查询结果进行封装
		Map<String,Object> map=new HashMap<>();
		map.put("user", result);//key要与页面取值方式保持一致
		map.put("roleIds", roleIds);
		return map;
	}

    @Override
    @RequiresPermissions("sys:user")
    public void save(User user) {
        try {
            passwordHelper.encryptPassword(user); //加密
            userMapper.save(user);
        } catch (Exception e) {
            throw new ResultException(ResultEnums.INNER_ERROR);
        }
    }

    @Override
    @RequiresPermissions("sys:user")
    public void update(User user) {
        try {
            if (user.getPassword() != null && !"".equals(user.getPassword())) {
                passwordHelper.encryptPassword(user); //加密
            }
            userMapper.update(user);
        } catch (Exception e) {
            throw new ResultException(ResultEnums.INNER_ERROR);
        }
    }

    @Override
    @RequiresPermissions("sys:user")
    public void delete(Long... ids) {
        try {
            for (long id : ids) {
                userMapper.delete(id);
            }
        } catch (Exception e) {
            throw new ResultException(ResultEnums.INNER_ERROR);
        }
    }

    @Override
    @RequiresPermissions("sys:user")
    public User findByName(String username) {
        return userMapper.findByName(username);
    }
    
    @RequiresPermissions("sys:user")
	@Override
	public int validById(Integer id, 
			Integer valid,
			String modifiedUser) {
		//1.合法性验证
		if(id==null||id<=0)
			throw new ServiceException("参数不合法,id="+id);
		if(valid!=1&&valid!=0)
			throw new ServiceException("参数不合法,valie="+valid);
		if(StringUtils.isEmpty(modifiedUser))
			throw new ServiceException("修改用户不能为空");
		//2.执行禁用或启用操作
		int rows=0;
		try{
			rows=userMapper.validById(id, valid, modifiedUser);
		}catch(Throwable e){
			e.printStackTrace();
			//报警,给维护人员发短信
			throw new ServiceException("底层正在维护");
		}
		//3.判定结果,并返回
		if(rows==0)
		throw new ServiceException("此记录可能已经不存在");
		return rows;
	}

	@Override
    @RequiresPermissions("sys:user")
	public PageObject<User> doFindPageObjects(String username, Integer pageCurrent) {
		int pageSize = 3;
		PageHelper.startPage(pageCurrent, pageSize);
		User user = new User();
		user.setUsername(username);
        Page page = userMapper.findByPage(user);
        if(page.getTotal()==0) {
        	throw new ServiceException("记录不存在");
        }
        PageObject<User> pageObject=
        		PageUtils.newPageObject((int)page.getTotal(),
        				page.getResult(), pageSize, pageCurrent);
        		return pageObject;
	}
	
	@RequestCache
	@RequestLog("用户查询")
	@Override
    @RequiresPermissions("sys:user")
	public int findObjectByColumn(String columnName,
			String columnValue) {
		if(StringUtils.isEmpty(columnName))
	    throw new IllegalArgumentException("参数名不能为空");
		if(StringUtils.isEmpty(columnValue))
		throw new IllegalArgumentException("参数值不能为空");
		int count=userMapper.findObjectByColumn(columnName, columnValue);
		if(count>0) 
		throw new ServiceException(columnValue + "已存在");
		return count;
	}
	
	@Override
    @RequiresPermissions("sys:user")
	public int updateObject(User entity, 
			Integer[] roleIds) {
		//1.对参数进行校验
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		//....
		//2.保存用户自身信息
		int rows=userMapper.updateObject(entity);
		//3.保存用户与角色关系数据
		sysUserRoleMapper.deleteObjectsByUserId((int)entity.getId());
		sysUserRoleMapper.insertObject(
				(int)entity.getId(),
				roleIds);
		return rows;
	}
	
	@Override
    @RequiresPermissions("sys:user")
	public void saveObject(User entity, 
			Integer[] roleIds) {
		
		//1.对参数进行校验
		if(entity==null)
		throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
	    throw new IllegalArgumentException("用户名不能为空");
		if(StringUtils.isEmpty(entity.getPassword()))
		throw new IllegalArgumentException("密码不能为空");
		if(roleIds==null||roleIds.length==0)
	    throw new IllegalArgumentException("必须指定其角色");
		//....
		//2.保存用户自身信息
		//2.1对密码进行加密
		//使用随机字符串作为salt(盐值)
		String salt=UUID.randomUUID().toString();
		entity.setSalt(salt);
		//密码，盐值加密
		SimpleHash hash=new SimpleHash(
				"MD5",//algorithmName
				 entity.getPassword(),//source
				 salt,
				 2);//hashIterations
		entity.setPassword(hash.toHex());
		//保存用户自身信息
		String username=ShiroUtils.getUser();
		entity.setCreatedUser(username);
		entity.setModifiedUser(username);
		System.out.println(entity);
		userMapper.insertObject(entity);
		System.out.println(entity);
		//3.保存用户与角色关系数据
		sysUserRoleMapper.insertObject(
				(int)entity.getId(),
				roleIds);
	}
}
