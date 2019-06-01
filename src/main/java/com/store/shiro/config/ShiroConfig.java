package com.store.shiro.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.store.shiro.UserRealm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;



//@Configuration标注在类上，相当于把该类作为spring的xml配置文件中的<beans>，
@Configuration
public class ShiroConfig {
	//@Bean标注在方法上(返回某个实例的方法)，等价于spring的xml配置文件中的<bean>，作用为：注册bean对象
	@Bean
	UserRealm userRealm() {
		UserRealm userRealm = new UserRealm();
		return userRealm;
	}
	@Bean(name = "sessionIdGenerator")
	public JavaUuidSessionIdGenerator sessionIdGenerator() {
	    return new JavaUuidSessionIdGenerator();
	}

	@Bean(name = "sessionIdCookie")
	public SimpleCookie sessionIdCookie() {
	    SimpleCookie cookie = new SimpleCookie();
	    cookie.setName("WEBSID");
	 /*   cookie.setName("JSESSIONID");*/
	    cookie.setHttpOnly(true);
	    //设置cookie有效时间
	    cookie.setMaxAge(24*60*60*1000);
	    return cookie;
	}

	@Bean(name = "sessionDao")
	public EnterpriseCacheSessionDAO sessionDao() {     
	    EnterpriseCacheSessionDAO sessionDao = new EnterpriseCacheSessionDAO();
	    sessionDao.setActiveSessionsCacheName("shiro-activeSessionCache");
	    sessionDao.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
	    return sessionDao;
	}

	@Bean(name = "sessionManager")
	public DefaultWebSessionManager sessionManager() {
	    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
	    //session的失效时长，单位毫秒 
	    sessionManager.setGlobalSessionTimeout(24*60*60*1000);
	     /*删除失效的session*/ 
	    sessionManager.setDeleteInvalidSessions(true);
	    /*定时检查失效的session*/
	    sessionManager.setSessionValidationSchedulerEnabled(true);
	    /*去掉URL中的JSESSIONID*/
	    sessionManager.setSessionIdUrlRewritingEnabled(false);
	    // sessionManager.setSessionValidationScheduler(new QuartzSessionValidationScheduler());
	    sessionManager.setSessionDAO(sessionDao());
	    sessionManager.setSessionIdCookieEnabled(true);
	    sessionManager.setSessionIdCookie(sessionIdCookie());
	    return sessionManager;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
	    return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
	    DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
	    daap.setProxyTargetClass(true);
	    return daap;
	}

	
	@Bean
	SecurityManager securityManager() {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(userRealm());
		manager.setSessionManager(sessionManager());
		return manager;
	}

	@Bean
	ShiroFilterFactoryBean shiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
	    // 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager());
		 // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/back/login");
		 // 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/back/index");
		 // 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/back/error/403");
		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new HashMap<>();
		 // 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/back/api/sso/login", "anon");
		// 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/back/logout", "logout");
		// <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
	    
		/*其权限过滤器及配置释义
		anon:例子/admins/**=anon 没有参数，表示可以匿名使用。
		authc:例如/admins/user/**=authc表示需要认证(登录)才能使用，没有参数
		roles(角色)：例子/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。
		perms（权限）：例子/admins/user/**=perms[user:add:*],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。
		rest：例子/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。
		port：例子/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString
		是你访问的url里的？后面的参数。
		authcBasic：例如/admins/user/**=authcBasic没有参数表示httpBasic认证
		ssl:例子/admins/user/**=ssl没有参数，表示安全的url请求，协议为https
		user:例如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查*/
		filterChainDefinitionMap.put("/back/**", "authc");
		//Shiro拦截器工厂类注入
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}
    //注入这个bean用来管理shiro一些bean的生命周期 生命周期就是初始化 与 销毁的管理
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

	/**
     * Add.5.2
     *   自动代理所有的advisor:
     *   由Advisor决定对哪些类的方法进行AOP代理。
     */
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
		proxyCreator.setProxyTargetClass(true);
		return proxyCreator;
	}
    
	 /**
     *  Add.5.1
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			@Qualifier("securityManager") SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

}
