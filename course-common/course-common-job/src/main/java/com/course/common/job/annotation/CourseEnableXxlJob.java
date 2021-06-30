package com.course.common.job.annotation;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;

import com.course.common.job.XxlJobAutoConfiguration;

/**
 * 激活xxl-job配置
 * 
 * @author qinlei
 * @date 2021/6/30 下午4:49
 */
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(XxlJobAutoConfiguration.class)
public @interface CourseEnableXxlJob {
}
