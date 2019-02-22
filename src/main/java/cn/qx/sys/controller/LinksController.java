package cn.qx.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.qx.common.enums.ResultEnums;
import cn.qx.common.util.CheckValue;
import cn.qx.common.vo.Result;
import cn.qx.common.vo.StatusCode;
import cn.qx.sys.entity.Links;
import cn.qx.sys.service.LinksService;

/**
 * 
 * @author STK_Tofu
 * @date 2019年2月22日
 */
@RestController
@RequestMapping("/links")
public class LinksController {

    @Autowired
    private LinksService linksService;

    @RequestMapping(value = "/findAllCount", method = RequestMethod.GET)
    public Result findAllCount() {
        return new Result(StatusCode.SUCCESS, linksService.findAllCount());
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public Result findAll() {
        return new Result(StatusCode.SUCCESS, linksService.findAllCount());
    }

    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    public Result findByPage(Links links,
                             @RequestParam(value = "pageCode", required = false) Integer pageCode,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (CheckValue.checkPage(pageCode, pageSize)) {
            return new Result(StatusCode.SUCCESS, linksService.findByPage(links, pageCode, pageSize));
        }
        return new Result(StatusCode.PARAMETER_ERROR, ResultEnums.PARAMETER_ERROR);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public Result findById(@RequestParam("id") Long id) {
        if (CheckValue.checkId(id)) {
            return new Result(StatusCode.SUCCESS, linksService.findById(id));
        }
        return new Result(StatusCode.PARAMETER_ERROR, ResultEnums.PARAMETER_ERROR);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody Links links) {
        if (CheckValue.checkObj(links)) {
            try {
                linksService.save(links);
                return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(StatusCode.ERROR, e.getMessage());
            }
        }
        return new Result(StatusCode.PARAMETER_ERROR, ResultEnums.PARAMETER_ERROR);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result update(@RequestBody Links links) {
        if (CheckValue.checkObj(links)) {
            try {
                linksService.update(links);
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
                linksService.delete(ids);
                return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(StatusCode.ERROR, e.getMessage());
            }
        }
        return new Result(StatusCode.PARAMETER_ERROR, ResultEnums.PARAMETER_ERROR);
    }
}
