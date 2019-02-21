package cn.qx.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import cn.qx.common.web.AccessInterceptor;

/**
 * 配置视图解析器、拦截器，代替spring-mvc.xml
 * 
 * @author Satone
 * @date 2019年2月20日下午8:33:30
 */
@Configuration
@ComponentScan(value = "cn.qx", useDefaultFilters = false, // 取消默认过滤器
        includeFilters = { // 只加载有指定注解修饰的类
                @Filter(type = FilterType.ANNOTATION, classes = { Controller.class, ControllerAdvice.class,RestController.class}) })
@EnableWebMvc // 启用mvc默认配置(内置很多类型转换器bean对象)
public class AppMvcConfig extends WebMvcConfigurerAdapter {
    public final static String CHARACTER_ENCODING = "UTF-8";

    /**
     * thymeleaf模板引擎参数
     */
    public final static String TEMPLATE_PREFIX = "/WEB-INF/templates/";
    public final static String TEMPLATE_SUFFIX = ".html";
    public final static Boolean TEMPLATE_CACHEABLE = false;
    public final static String TEMPLATE_MODE = "HTML5";
    public final static Integer TEMPLATE_ORDER = 1;

//	@Override
//	public void configureViewResolvers(
//			ViewResolverRegistry registry) {
//		registry.jsp("/WEB-INF/templates/",".html");
//	}
    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 构建拦截器对象
        AccessInterceptor interceptor = new AccessInterceptor();
        // 注册拦截器对象
        registry.addInterceptor(interceptor)
                // 配置要拦截的url
                .addPathPatterns("/user/doLogin.do");
    }

    /**
     * 模板解析器
     *
     * @return
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix(TEMPLATE_PREFIX);
        templateResolver.setSuffix(TEMPLATE_SUFFIX);
        templateResolver.setCacheable(TEMPLATE_CACHEABLE);
        templateResolver.setCharacterEncoding(CHARACTER_ENCODING);
        templateResolver.setTemplateMode(TEMPLATE_MODE);
        templateResolver.setOrder(TEMPLATE_ORDER);
        return templateResolver;
    }

    /**
     * 模板引擎
     *
     * @return
     */
    @Bean
    public SpringTemplateEngine springTemplateEngine(SpringResourceTemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    /**
     * 视图解析器
     *
     * @return
     */
    @Bean
    public ThymeleafViewResolver viewResolver(SpringTemplateEngine springTemplateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(springTemplateEngine);
        viewResolver.setCharacterEncoding(CHARACTER_ENCODING);
        return viewResolver;
    }
    
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/site/**").addResourceLocations("classpath:/static/site/");
//    }
}
