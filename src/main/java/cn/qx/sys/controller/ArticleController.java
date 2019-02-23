package cn.qx.sys.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.qx.common.enums.ResultEnums;
import cn.qx.common.util.CheckValue;
import cn.qx.common.vo.Result;
import cn.qx.common.vo.StatusCode;
import cn.qx.sys.entity.Article;
import cn.qx.sys.entity.Tags;
import cn.qx.sys.service.ArticleService;
import cn.qx.sys.service.ArticleTagsService;

/**
 * 
 * @author STK_Tofu
 * @date 2019年2月22日
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleTagsService articleTagsService;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public Result findAll() {
        return new Result(StatusCode.SUCCESS, articleService.findAll());
    }

    @RequestMapping(value = "/findAllCount", method = RequestMethod.GET)
    public Result findAllCount() {
        return new Result(StatusCode.SUCCESS, articleService.findAllCount());
    }

    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    public Result findByPage(Article article,
                             @RequestParam(value = "pageCode", required = false) Integer pageCode,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (CheckValue.checkPage(pageCode, pageSize)) {
            return new Result(StatusCode.SUCCESS, articleService.findByPage(article, pageCode, pageSize));
        } else {
            return new Result(StatusCode.PARAMETER_ERROR, ResultEnums.PARAMETER_ERROR);
        }
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public Result findById(@RequestParam("id") String ids, Model model) throws JsonProcessingException {
        // 前端获取url为“4.do”
        Long id = Long.parseLong(ids.split("\\.")[0]);
        if (CheckValue.checkId(id)) {
            Article article = articleService.findById(id);
            if (CheckValue.checkObj(article)) {
                if (article.getId() != 0) {
                    List<String> tags = new ArrayList<>();
                    List<Tags> tagsList = articleTagsService.findByArticleId(article.getId());
                    for (Tags t : tagsList) {
                        tags.add(t.getName());
                    }
                    article.setTags(new ObjectMapper().writeValueAsString(tags));
                } else {
                    return new Result(StatusCode.ERROR, ResultEnums.ERROR);
                }
            }
            return new Result(StatusCode.SUCCESS, article);
        }
        return new Result(StatusCode.PARAMETER_ERROR, ResultEnums.PARAMETER_ERROR);
    }

    @RequestMapping(value = "/findTags", method = RequestMethod.GET)
    public Result findTags(@RequestParam("id") Long id) {
        if (CheckValue.checkId(id)) {
            List<String> list = new ArrayList<String>();
            List<Tags> tagsList = articleTagsService.findByArticleId(id);
            for (Tags t : tagsList) {
                list.add(t.getName());
            }
            return new Result(StatusCode.SUCCESS, list);
        } else {
            return new Result(StatusCode.PARAMETER_ERROR, ResultEnums.PARAMETER_ERROR);
        }
    }

    @RequestMapping(value = "/findArchives", method = RequestMethod.GET)
    public Result findArchives() {
        return new Result(StatusCode.SUCCESS, articleService.findArchives());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@Validated @RequestBody Article article) {
        if (CheckValue.checkObj(article)) {
            try {
                articleService.save(article);
                return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(StatusCode.ERROR, e.getMessage());
            }
        }
        return new Result(StatusCode.PARAMETER_ERROR, ResultEnums.PARAMETER_ERROR);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result update(@RequestBody Article article) {
        if (CheckValue.checkObj(article)) {
            try {
                System.out.println("ArticleController.update()");
                articleService.update(article);
                return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(StatusCode.ERROR, e.getMessage());
            }
        }
        return new Result(StatusCode.PARAMETER_ERROR, ResultEnums.PARAMETER_ERROR);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(@RequestBody Long... ids) {
        if (CheckValue.checkIds(ids)) {
            try {
                articleService.delete(ids);
                return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(StatusCode.ERROR, e.getMessage());
            }
        }
        return new Result(StatusCode.PARAMETER_ERROR, ResultEnums.PARAMETER_ERROR);
    }
}
