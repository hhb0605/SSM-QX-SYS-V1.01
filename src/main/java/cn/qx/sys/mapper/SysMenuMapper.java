package cn.qx.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.qx.common.vo.Node;
import cn.qx.sys.entity.Menu;

/**
 * 定义菜单持久层对象，处理数据访问操作
 * 定义菜单查询方法，查询所有菜单以及上级菜单信息（取id和名字）
 * @author Administrator
 *
 */
public interface SysMenuMapper {
	List<Map<String,Object>> findObjects();
	/**
	  * 根据菜单id统计子菜单的个数
	  * @param id
	  * @return
	  */
	 int getChildCount(Integer id);
	 
	 /**
	  * 根据id,执行菜单自身信息删除操作
	  * @param id
	  * @return
	  */
	 int deleteObject(Integer id);
	 
	 /**
	  * 查询菜单表中所有菜单信息
	  * @return
	  */
	 List<Node> findZtreeMenuNodes();

	 /**
	  * 插入数据的方法
	  */
	 int insertObject(Menu entity);
	 
	 /**
	  * 实现数据库中菜单的更新 
	  * @param entity
	  * @return
	  */
	 int updateObject(Menu entity);
	 
	 List<String> findPermissions(
				@Param("menuIds")
				Integer[] menuIds);

	 
}
