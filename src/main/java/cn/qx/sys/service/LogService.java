package cn.qx.sys.service;
import cn.qx.common.vo.PageObject;
import cn.qx.sys.entity.Log;
public interface LogService {
	 /**
	  * 基于id删除日志记录
	  * @param ids
	  * @return
	  */
	 int deleteObjects(Integer...ids);
	 
	 /**
	  * 基于查询条件分页查询当前日志行为数据
	  * @param username 页面输入的用户名
	  * @param pageCurrent 当前页码值
	  * @return
	  */
	 PageObject<Log> findPageObjects(
			 String username,
			 Integer pageCurrent);
}
