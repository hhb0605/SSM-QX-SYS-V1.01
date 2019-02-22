package cn.qx.common.utils;

import org.apache.shiro.SecurityUtils;

import cn.qx.sys.entity.User;

public class ShiroUtils {

	 public static User getUser(){
		 return (User)SecurityUtils
		.getSubject().getPrincipal();
	 }
}
