package cn.qx.common.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 配置数据源，代替spring-datasource.xml
 */
//读取properties，将内容封装到Envionment对象
@PropertySource("classpath:configs.properties")
@Lazy
@Configuration
public class AppDataSourceConfig {
    @Lazy(false)
    @Bean(value = "dataSource", initMethod = "init", destroyMethod = "close") // 等效于<bean id="" class="">
    public DataSource newDruidDataSource(Environment env) {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(env.getProperty("jdbcDriver"));
        ds.setUrl(env.getProperty("jdbcUrl"));
        ds.setUsername(env.getProperty("jdbcUser"));
        ds.setPassword(env.getProperty("jdbcPassword"));
        return ds;
    }

    /** 整合c3p0连接池 */
    @Bean("c3p0")
    public DataSource newC3p0DataSource(Environment env) throws Exception {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setDriverClass(env.getProperty("jdbcDriver"));
        ds.setJdbcUrl(env.getProperty("jdbcUrl"));
        ds.setUser(env.getProperty("jdbcUser"));
        ds.setPassword(env.getProperty("jdbcPassword"));
        return ds;
    }

}
