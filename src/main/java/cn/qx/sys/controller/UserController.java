package cn.qx.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import cn.qx.common.vo.JsonResult;
import cn.qx.sys.entity.User;

import cn.qx.common.enums.ResultEnums;
import cn.qx.common.util.CheckValue;
import cn.qx.common.vo.PasswordHelper;
import cn.qx.common.vo.Result;
import cn.qx.common.vo.StatusCode;
import cn.qx.sys.service.UserService;

/**
 * 
 * @author chibuqikendeji
 * @date 2019年2月22日
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordHelper passwordHelper;

    @RequestMapping(value = "/findByPage", method = RequestMethod.POST)
    public Result findByPage(User user,
                             @RequestParam(value = "pageCode", required = false) Integer pageCode,
                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (CheckValue.checkPage(pageCode, pageSize)) {
            return new Result(StatusCode.SUCCESS, userService.findByPage(user, pageCode, pageSize));
        }
        return new Result(StatusCode.PARAMETER_ERROR, ResultEnums.PARAMETER_ERROR);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public Result findById(@RequestParam("id") Long id) {
        if (CheckValue.checkId(id)) {
            return new Result(StatusCode.SUCCESS, userService.findById(id));
        }
        return new Result(StatusCode.PARAMETER_ERROR, ResultEnums.PARAMETER_ERROR);
    }
    
    @RequestMapping(value = "/findUserById", method = RequestMethod.GET)
    public JsonResult findUserById(@RequestParam("id") Long id) {
  		  return new JsonResult(
  		 userService.findObjectById(id));
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody User user) {
        if (CheckValue.checkObj(user)) {
            try {
                userService.save(user);
                return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(StatusCode.ERROR, e.getMessage());
            }
        }
        return new Result(StatusCode.PARAMETER_ERROR, ResultEnums.PARAMETER_ERROR);
    }

    @RequestMapping(value = "/doSaveObject")
	  public JsonResult doSaveObject(
			  User entity,Integer[]roleIds){
		  userService.saveObject(entity, roleIds);
		  return new JsonResult("save ok");
	  }
    
    
    @RequestMapping("/update")
    public Result update(@RequestBody User user) {
        if (CheckValue.checkObj(user)) {
            try {
                if (user.getPassword() != null && !"".equals(user.getPassword()) && user.getCheckPass() != null && !"".equals(user.getCheckPass())) {
                    //说明是更新密码操作
                    User u = userService.findByName((String) SecurityUtils.getSubject().getPrincipal());
                    u.setCheckPass(user.getPassword()); // 设置u.checkPass=旧密码
                    user.setSalt(u.getSalt()); // 设置user盐值
                    user.setPassword(user.getCheckPass()); // 设置user.password=旧密码
                    passwordHelper.encryptPassword(user); // 将新输入的密码加密
                    if (!u.getPassword().equals(user.getPassword())) {
                        logger.info("输入的旧密码不匹配：new:" + user.getPassword() + ", old:" + u.getPassword());
                        return new Result(StatusCode.ERROR, ResultEnums.LOGIN_CHECK_ERROR);
                    } else {
                        logger.info("输入的旧密码匹配，执行更新操作");
                        // 2.3 replace checkPassword and password
                        logger.info(u.getCheckPass());
                        user.setPassword(u.getCheckPass()); // new
                        // check success
                        userService.update(user);
                    }
                }
                return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(StatusCode.ERROR, e.getMessage());
            }
        }
        return new Result(StatusCode.PARAMETER_ERROR, ResultEnums.PARAMETER_ERROR);
    }
    
    @RequestMapping("doUpdateObject")
	  public JsonResult doUpdateObject(User entity,
			  Integer[] roleIds){
		  userService.updateObject(entity,
				  roleIds);
		  return new JsonResult("update ok");
	  }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(@RequestBody Long... ids) {
        if (CheckValue.checkIds(ids)) {
            try {
                userService.delete(ids);
                return new Result(StatusCode.SUCCESS, ResultEnums.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(StatusCode.ERROR, ResultEnums.ERROR);
            }
        }
        return new Result(StatusCode.PARAMETER_ERROR, ResultEnums.PARAMETER_ERROR);
    }
    @RequestMapping("doValidById")
	  public JsonResult doValidById(Integer id,
			  Integer valid){
    	String username=(String)SecurityUtils.getSubject()
				  .getPrincipal();//获取登录用户的身份信息
		  userService.validById(id, valid,username);
		  return new JsonResult("update ok");
	  }
    
    @RequestMapping("doFindPageObjects")
	  public JsonResult doFindPageObjects(
			  String username,Integer pageCurrent){
		  return new JsonResult(
		  userService.doFindPageObjects(
				  username, pageCurrent));
	  }
    @RequestMapping("doFindObjectByColumn")
    @ResponseBody
    public JsonResult doFindObjectByColumn(
   		 String columnName,
   		 String columnValue){
   	 return new JsonResult(
   		userService.findObjectByColumn(
   			columnName, columnValue));
    }
}
