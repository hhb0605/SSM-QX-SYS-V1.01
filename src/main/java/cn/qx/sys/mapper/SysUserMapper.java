package cn.qx.sys.mapper;

import org.apache.ibatis.annotations.Param;

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
    
    int validById(
			@Param("id")Integer id,
			@Param("valid")Integer valid,
			@Param("modifiedUser")String modifiedUser);
    int findObjectByColumn(
    		@Param("columnName")String columnName,
    		@Param("columnValue")String columnValue);
    int updateObject(User entity);
    int insertObject(User entity);
    	
    int updateInfo(User user);
}
