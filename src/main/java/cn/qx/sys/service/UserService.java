package cn.qx.sys.service;


import cn.qx.common.vo.PageObject;
import cn.qx.sys.entity.User;

/**
 * 
 * @author STK_Tofu
 * @date 2019年2月21日
 */
public interface UserService extends BaseService<User> {

    User findByName(String username);
    /**
	  * 禁用或启用用户
	  * @param id 用户id
	  * @param valid 状态值
	  * @param modifiedUser
	  * @return
	  */
    int validById(Integer id,Integer valid,String modifiedUser);
    
    PageObject<User> doFindPageObjects(String username,
      		 Integer pageCurrent);
    
    int findObjectByColumn(String columnName,String columnValue);
    int updateObject(User entity,Integer[]roleIds);
    
	void saveObject(User entity, Integer[] roleIds);
}
