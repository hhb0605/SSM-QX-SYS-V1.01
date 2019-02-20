package cn.qx.common.config;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
/**
 * tomcat 启动时会加载此类，然后执行相关方法
 * 完成初始化动作，代替web.xml
 * @author Satone
 * @date 2019年2月20日下午8:40:23
 */
public class AppWebInitializer extends 
    AbstractAnnotationConfigDispatcherServletInitializer {
	//此类对象在执行时首先会执行onStartup方法完成一些初始化操作
	//并且会注册spring mvc前端控制器
    @Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		//super.onStartup(servletContext);
		registerContextLoaderListener(servletContext);
		registerFilter(servletContext);
		registerDispatcherServlet(servletContext);
	}
    //注册shiro中的核心过滤器
    private void registerFilter(ServletContext servletContext) {
		//注册Filter对象
		//什么时候需要采用此方式进行注册?
		//项目没有web.xml并且此filter不是自己写的
		FilterRegistration.Dynamic dy=
		servletContext.addFilter("filterProxy",
				DelegatingFilterProxy.class);
		dy.setInitParameter("targetBeanName","shiroFilterFactory");
		dy.addMappingForUrlPatterns(
				null,//EnumSet<DispatcherType>
				false,"/*");//url-pattern
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		//return new Class[]{AppDataSourceConfig.class,AppMyBatisConfig.class};
		return new Class[]{AppRootConfig.class};
	}
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[]{AppMvcConfig.class};
	}
	@Override
	protected String[] getServletMappings() {
		//由前端控制器处理所有以.do结尾的请求
		return new String[]{"*.do"};
	}
}
