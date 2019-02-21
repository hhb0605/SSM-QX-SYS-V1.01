package cn.qx.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.qx.common.enums.ResultEnums;
import cn.qx.common.exception.ResultException;
import cn.qx.common.vo.PageBean;
import cn.qx.common.vo.PasswordHelper;
import cn.qx.sys.entity.User;
import cn.qx.sys.mapper.SysUserMapper;
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

    @Override
    public Long findAllCount() {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public PageBean findByPage(User user, int pageCode, int pageSize) {
        PageHelper.startPage(pageCode, pageSize);
        Page page = userMapper.findByPage(user);
        return new PageBean(page.getTotal(), page.getResult());
    }

    @Override
    public User findById(long id) {
        return userMapper.findById(id);
    }

    @Override
    public void save(User user) {
        try {
            passwordHelper.encryptPassword(user); //加密
            userMapper.save(user);
        } catch (Exception e) {
            throw new ResultException(ResultEnums.INNER_ERROR);
        }
    }

    @Override
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
    public User findByName(String username) {
        return userMapper.findByName(username);
    }
}
