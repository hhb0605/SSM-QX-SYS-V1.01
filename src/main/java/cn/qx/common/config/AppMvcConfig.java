package cn.qx.common.config;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.qx.common.web.AccessInterceptor;

/**
 * 配置视图解析器、拦截器，代替spring-mvc.xml
 * @author Satone
 * @date 2019年2月20日下午8:33:30
 */
@ComponentScan(
    value="com.db",
    useDefaultFilters=false,//取消默认过滤器
    includeFilters={//只加载有指定注解修饰的类
      @Filter(type=FilterType.ANNOTATION,
              classes={Controller.class,
                       ControllerAdvice.class})})
@EnableWebMvc //启用mvc默认配置(内置很多类型转换器bean对象)
public class AppMvcConfig extends WebMvcConfigurerAdapter {
	@Override
	public void configureViewResolvers(
			ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/templates/",".html");
	}
	/**
	 * 注册拦截器
	 */
	@Override
	public void addInterceptors(
			InterceptorRegistry registry) {
		//构建拦截器对象
		AccessInterceptor interceptor=
		new AccessInterceptor();
		//注册拦截器对象
		registry.addInterceptor(interceptor)
		//配置要拦截的url
		.addPathPatterns("/user/doLogin.do");
	}
	
}






