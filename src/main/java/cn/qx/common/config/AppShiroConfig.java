package cn.qx.common.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 配置shiro，代替spring-shiro.xml
 * 
 * @author Satone
 * @date 2019年2月20日下午8:39:53
 */
@Configuration
public class AppShiroConfig {

    @Bean("securityManager")
    public SecurityManager newDefaultWebSecurityManager(AuthorizingRealm userRealm) {
        DefaultWebSecurityManager sManager = new DefaultWebSecurityManager();
        // 此时必须保证realm对象已经存在了
        sManager.setRealm(userRealm);
        return sManager;
    }

    @Bean("shiroFilterFactory")
    public ShiroFilterFactoryBean newShiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/login.do");

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/admin/css/**", "anon");
        filterChainDefinitionMap.put("/admin/img/**", "anon");
        filterChainDefinitionMap.put("/admin/js/**", "anon");
        filterChainDefinitionMap.put("/lib/**", "anon");
        filterChainDefinitionMap.put("/public/admin/**", "anon");
        filterChainDefinitionMap.put("/public/site/**", "anon");
        filterChainDefinitionMap.put("/site/css/**", "anon");
        filterChainDefinitionMap.put("/site/js/**", "anon");
        filterChainDefinitionMap.put("/site/img/**", "anon");

        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/site", "anon");
        filterChainDefinitionMap.put("/site/**", "anon");

        filterChainDefinitionMap.put("/logout", "logout");

        filterChainDefinitionMap.put("/login.do", "anon");
        filterChainDefinitionMap.put("/admin/login.do", "anon");

        filterChainDefinitionMap.put("/admin/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    // ===========================
    /**
     * 负责shiro中相关bean对象(代理对象 )的声明周期管理
     * 
     * @return
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor newLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 通过此对象底层创建代理对象(需要进行授权访问的service)
     * 
     * @return
     */
    @DependsOn(value = "lifecycleBeanPostProcessor")
    @Bean
    public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    /**
     * 授权属性设置(例如由谁进行授权操作等)
     * 
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor newAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor bean = new AuthorizationAttributeSourceAdvisor();
        bean.setSecurityManager(securityManager);
        return bean;
    }

}
