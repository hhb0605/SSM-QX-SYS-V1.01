package cn.qx.sys.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PageController {

      /**
       * 返回分页页面
       */
      @RequestMapping("{rest}/doPageUI")
      public String doPageUI(){
    	 //...
      	 return "admin/page/common/page";
      }  
      
      @RequestMapping("user/doUserEditUI")
	  public String doUserEditUI(){
		  return "admin/page/user_edit";
	  }
      @RequestMapping("user/doUserListUI")
	  public String doUserListUI(){
		  return "admin/page/user";
	  }
}
