package cn.qx.sys.service;

import java.util.List;
import java.util.Map;

import cn.qx.common.vo.Node;
import cn.qx.sys.entity.Menu;


/**
 * 定义菜单业务接口，负责处理菜单模块业务
 * 定义业务方法，访问dao层方法获取菜单信息
 * @author Administrator
 *
 */
public interface MenuService {
	/**
	 * 查询所有菜单信息以及本菜单对应
	 * 的上级菜单的名字
	 * @return
	 */
	List<Map<String,Object>> findObjects();
	/**
	 * 基于id删除记录
	 * 返回菜单的行数
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	
	// 查询菜单表中所有菜单信息
	List<Node> findZtreeMenuNodes();
	
	// 将数据写入数据库
	int insertObject(Menu entity);
	
	// 添加菜单数据
	int updateObject(Menu entity);
}
