package com.course.common.security.annotation;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.course.common.security.config.resources.ResourceServerAutoConfiguration;
import com.course.common.security.config.resources.SecurityBeanDefinitionRegistrar;

/**
 * 资源服务器注解
 * 
 * @author qinlei
 * @date 2021/8/2 下午12:58
 */
@Documented
@Inherited
@EnableResourceServer
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ ResourceServerAutoConfiguration.class, SecurityBeanDefinitionRegistrar.class })
public @interface CourseEnableResourceServer {
}
