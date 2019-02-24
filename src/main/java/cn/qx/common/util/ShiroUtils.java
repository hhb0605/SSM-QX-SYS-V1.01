package cn.qx.common.util;

import org.apache.shiro.SecurityUtils;

public class ShiroUtils {

	 public static String getUser(){
		 return (String)SecurityUtils
		.getSubject().getPrincipal();
	 }
}
