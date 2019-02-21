package cn.qx.common.config;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.github.pagehelper.PageInterceptor;
/**
 * 配置事务、会话工厂、mapperScan，代替spring-mybatis.xml
 * @author Satone
 * @date 2019年2月20日下午8:34:20
 */
@MapperScan(basePackages="cn.qx.**.mapper")
@Configuration//通过此注解声明这是配置文件
public class AppMyBatisConfig {
	 //假如bean没有指定名字,此bean的默认名字为方法名
	 @Bean("sqlSessionFactory")
	 public SqlSessionFactoryBean newSqlSesionFactoryBean(
		 DataSource dataSource)throws IOException{
		 //1.构建bean对象
		 SqlSessionFactoryBean fBean=
		 new SqlSessionFactoryBean();
		 //2.配置数据源
		 fBean.setDataSource(dataSource);
		 //3.设置映射文件
		 Resource[] mapperLocations=
         new PathMatchingResourcePatternResolver()
		 .getResources("classpath:mapper/sys/*Mapper.xml");
		 fBean.setMapperLocations(mapperLocations);
		 // 4.配置
		 PageInterceptor pageInterceptor = new PageInterceptor();
	        Properties properties = new Properties();
	        properties.setProperty("helperDialect", "mysql");
	        pageInterceptor.setProperties(properties);
	        fBean.setPlugins(new PageInterceptor[] {pageInterceptor});
		 return fBean;
	 }
	 /**配置事务管理对象：开启事务，提交事务，回滚事务，释放资源...*/
	 @Bean("txManager")
	 public DataSourceTransactionManager 
	     newTransactionManager(
	    		 @Autowired DataSource dataSource){
		 DataSourceTransactionManager tManager=
		 new DataSourceTransactionManager();
		 tManager.setDataSource(dataSource);
		 return tManager;
	 }
	 
}




