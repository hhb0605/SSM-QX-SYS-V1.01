package cn.qx.common.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
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
    
    @Bean("rememberMeCookie")
    public SimpleCookie rememberMeCookie() {
    	SimpleCookie cookie = new SimpleCookie("rememberMe");
    	cookie.setHttpOnly(true);
    	cookie.setMaxAge(3600000);
    	return cookie;
    }
    
    // 设置RememberMeManager，实现RememberMe
    @Bean("rememberMeManager")
    public CookieRememberMeManager rememberMeManager() {
    	CookieRememberMeManager remenberCookie = new CookieRememberMeManager();
    	remenberCookie.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
    	remenberCookie.setCookie(rememberMeCookie());
    	return remenberCookie;
    }
    
    /*@Bean("sessionManager")
    public DefaultWebSessionManager sessionManager() {
    	DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    	sessionManager.setGlobalSessionTimeout(10000);
    	sessionManager.setDeleteInvalidSessions(true);
    	return sessionManager;
    }*/
    
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

        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/login.do", "anon");
        filterChainDefinitionMap.put("/admin/login.do", "anon");

        filterChainDefinitionMap.put("/admin.do", "user");
        filterChainDefinitionMap.put("/admin/**", "user");
        filterChainDefinitionMap.put("/admin/log.do", "authc");

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
