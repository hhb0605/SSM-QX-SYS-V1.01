package cn.qx.common.config;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 配置ComponentScan，启用AOP、事务，代替spring-configs.xml
 * @author Satone
 * @date 2019年2月20日下午8:36:09
 */
@ComponentScan(
	value="cn.qx"
    ,excludeFilters={//要排除加载的类(例如使用controller注解修饰的类不进行加载)
    @Filter(type=FilterType.ANNOTATION,//约束classes属性中的内容
    		classes={Controller.class,
    				 ControllerAdvice.class})}
	)
@EnableAspectJAutoProxy //启用AOP配置
@EnableTransactionManagement//启用注解方式的事务管理
public class AppRootConfig {
   
	
}









