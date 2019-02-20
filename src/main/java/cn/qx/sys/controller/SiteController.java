package cn.qx.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cn.qx.common.vo.PageBean;
import cn.qx.sys.entity.Article;
import cn.qx.sys.service.ArticleService;
import cn.qx.sys.service.CommentsService;

/**
 * 用于博客前端页面跳转的控制层 注意: 整个博客前端页面数据存在域对象中，前端通过Themeleaf模板引擎从域对象中拿数据。
 * 博客的后端admin则采用前后端分离的方式，Controller只负责返回JSON数据。
 * 
 * @author Satone
 * @date 2019年2月20日下午9:05:18
 */
@Controller
public class SiteController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentsService commentsService;

    @GetMapping(value = { "", "/", "/doIndex" })
    public String index(Model model) {

        // 初始化页面数据
        initIndex(1, model);
        initFooter(model);
        System.out.println("跳转首页");
        return "site/index";
    }

    /**
     * 初始化页面数据，将互数据存入到域对象中，页面使用Thymeleaf模板引擎提供的方法获取域对象中的数据
     *
     * @param pageCode
     * @param model
     */
    private void initIndex(Integer pageCode, Model model) {
        // 分页文章数据
        PageBean<Article> page = articleService.findByPageForSite(pageCode, 6);
        page.setTotal((long) Math.ceil((double) page.getTotal() / (double) 6));
        model.addAttribute("page", page); // 格式：[{...}, {...}, {...}]
        model.addAttribute("pageCode", pageCode); // 格式：1
    }

    private void initFooter(Model model) {
        // 网站footer显示数据
        model.addAttribute("articleList", articleService.findAll()); // 格式：[{...}, {...}, {...}]
        model.addAttribute("commentsList", commentsService.findAll()); // 格式：[{...}, {...}, {...}]
    }

}
