package cn.qx.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.qx.common.vo.JsonResult;
import cn.qx.sys.entity.Menu;
import cn.qx.sys.service.MenuService;

@Controller
@RequestMapping("/menu/")
public class MenuController {
	@Autowired
	private MenuService sysMenuService;
	@RequestMapping("doMenuListUI")
	public String doMenuListUI(){
		return "sys/menu_list";
	}
	
	@RequestMapping("doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects() {
		return new JsonResult(sysMenuService.findObjects());
	}
	
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id){
		sysMenuService.deleteObject(id);
		return new JsonResult("delete OK");
	}
	
	@RequestMapping("doMenuEditUI")
	public String doMenuEditUI() {
		return "sys/menu_edit";
	}
	
	@RequestMapping("doFindZtreeMenuNodes")
	@ResponseBody
	public JsonResult doFindZtreeMenuNodes(){
		return new JsonResult(
				sysMenuService.findZtreeMenuNodes());
	}
	
	@PostMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(Menu entity) {
		sysMenuService.insertObject(entity);
		return new JsonResult("save ok");
	}
	
	//更新数据
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(Menu entity){
		sysMenuService.updateObject(entity);
		return new JsonResult("update ok");
	}

}
