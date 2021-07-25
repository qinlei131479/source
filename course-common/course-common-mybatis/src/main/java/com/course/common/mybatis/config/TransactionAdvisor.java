package com.course.common.mybatis.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import lombok.RequiredArgsConstructor;

/**
 * spring 统一事务管理
 *
 * @author qinlei
 * @date 2021/6/23 下午5:28
 */
@Aspect
@Component
@RequiredArgsConstructor
public class TransactionAdvisor {

	private final TransactionManager transactionManager;

	@Bean
	public Advisor txAdviceAdvisor() {
		// 需要事务
		DefaultTransactionAttribute required = new DefaultTransactionAttribute();
		required.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		// 只读事务
		DefaultTransactionAttribute supports = new DefaultTransactionAttribute();
		supports.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
		supports.setReadOnly(true);
		// 配置方法
		NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
		source.addTransactionalMethod("*", required);
		source.addTransactionalMethod("find*", supports);
		source.addTransactionalMethod("select*", supports);
		source.addTransactionalMethod("get*", supports);
		// 配置切点
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(* *..service*..*ServiceImpl.*(..))");
		return new DefaultPointcutAdvisor(pointcut, new TransactionInterceptor(transactionManager, source));
	}

}
