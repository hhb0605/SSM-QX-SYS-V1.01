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
}
