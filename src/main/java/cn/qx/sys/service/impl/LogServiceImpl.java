package cn.qx.sys.service.impl;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.qx.common.annotation.RequestLog;
import cn.qx.common.exception.ServiceException;
import cn.qx.common.vo.PageObject;
import cn.qx.sys.mapper.SysLogMapper;
import cn.qx.sys.entity.Log;
import cn.qx.sys.service.LogService;
/**
 * 
 * @author hhb
 * @date 2019年2月22日
 */
@Service("sysLogService")
public class LogServiceImpl implements LogService {
    @Autowired 
	@Qualifier("sysLogMapper")
    private SysLogMapper sysLogMapper;
    
    /**
     * @RequiresPermissions 注解修饰方法时，
     * 表示访问此方法需要进行授权才可以访问，
     * 假如认证用户具备"sys:log:delete"这个
     * 权限标识，则可以访问。
     */

    @RequestLog("删除操作")
    @Override
    @RequiresPermissions("sys:log")
    public int deleteObjects(Integer... ids) {
    	//1.参数有效性验证
    	if(ids==null||ids.length==0)
    	throw new IllegalArgumentException("id值无效");
    	//2.执行删除操作
    	int rows=-1;
    	try{
    	 rows=sysLogMapper.deleteObjects(ids);
    	}catch(Throwable e){
    	 //给运维人员发短信
    	 //写错误日志
    	 throw e;
    	}
    	//3.验证删除结果
    	if(rows==0)
    	throw new ServiceException("记录可能已经不存在");
    	//4.返回结果
    	return rows;
    }
	@Override
    @RequiresPermissions("sys:log")
	public PageObject<Log> findPageObjects(
		String username, Integer pageCurrent) {
	    //1.验证参数的有效性
		if(pageCurrent==null||pageCurrent<1)
		throw new IllegalArgumentException("页码值不正确");
		//2.基于用户名查询总记录数并进行相关判定
		int rowCount=sysLogMapper.getRowCount(username);
		if(rowCount==0)
		throw new ServiceException("没有找到对应记录");
		//3.基于用户名以及当前页码值查询当前的记录
		int pageSize=3;
		int startIndex=(pageCurrent-1)*pageSize;
		List<Log> records=
		sysLogMapper.findPageObjects(username, startIndex, pageSize);
		//4.封装两次结果并返回.
		PageObject<Log> po=new PageObject<>();
		po.setRowCount(rowCount);
		po.setRecords(records);
		po.setPageSize(pageSize);
		po.setPageCurrent(pageCurrent);
		/*int pageCount=rowCount/pageSize;
		if(rowCount%pageSize!=0){
			pageCount++;
		}*/
		po.setPageCount((rowCount-1)/pageSize+1);
		return po;
	}

}
