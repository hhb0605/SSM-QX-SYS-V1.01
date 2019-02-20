package cn.qx.sys.mapper;

import com.github.pagehelper.Page;

import cn.qx.sys.entity.User;

/**
 * 
 * @author Satone
 * @date 2019年2月20日下午9:21:59
 */
public interface SysUserMapper {

    Page<User> findByPage(User user);

    User findById(long id);

    void save(User user);

    void update(User user);

    void delete(long id);

    User findByName(String username);
}
