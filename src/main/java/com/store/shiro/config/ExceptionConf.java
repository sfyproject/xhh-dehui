package com.store.shiro.config;

import java.util.Properties;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
//@Configuration用于定义配置类，可替换xml配置文件，被注解的类内部包含有一个或多个被@Bean注解的方法，这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，并用于构建bean定义，初始化Spring容器。
@Configuration
public class ExceptionConf {

	@Bean
    public SimpleMappingExceptionResolver resolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        /*properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/unAuth");*/
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/error/403");
        resolver.setExceptionMappings(properties);
        return resolver;
    }
}
