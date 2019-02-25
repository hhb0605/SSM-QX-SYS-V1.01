package cn.qx.sys.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.qx.common.util.ExportExcel;
import cn.qx.common.vo.JsonResult;
import cn.qx.common.vo.PageObject;
import cn.qx.sys.entity.Log;
import cn.qx.sys.service.LogService;
/**
 * 
 * @author hhb
 * @date 2019年2月23日
 */
@Controller
@RequestMapping("/log/")
public class LogController {
	@Autowired
	private LogService sysLogService;
	/**
	 * 返回日志页面
	 * @return
	 */	
	@RequestMapping("doLogListUI")
	public String doLogListUI(){
		return "sys/log";
	}
	
	@PostMapping("doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteObjects(Integer...ids){
		System.out.println("sysLogService="+sysLogService.getClass().getName());
		sysLogService.deleteObjects(ids);
		return new JsonResult("delete ok");
	}
	
	@GetMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(
			String username,
			Integer pageCurrent){
		PageObject<Log> pageObject=
		sysLogService.findPageObjects(username, pageCurrent);
		return new JsonResult(pageObject);
	}
	
	@ResponseBody
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response) {
	    Log log = new Log();//创建实体类对象
	    List<Log> encryptList = sysLogService.getEncryptDeviceForExcel(log);
	    ExportExcel<Log> ee= new ExportExcel<Log>();
	    String[] headers = {"id","username","operation","method","params","time","ip","createTime"};
	    String fileName = "qx_log";
	    ee.exportExcel(headers,encryptList,fileName,response);
	}
}





