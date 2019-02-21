package cn.qx.sys.service;

import cn.qx.sys.entity.User;

/**
 * 
 * @author STK_Tofu
 * @date 2019年2月21日
 */
public interface UserService extends BaseService<User> {

    User findByName(String username);
}
