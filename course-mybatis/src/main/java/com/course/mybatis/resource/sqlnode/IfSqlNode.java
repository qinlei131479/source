package com.course.mybatis.resource.sqlnode;

import com.course.mybatis.resource.utils.OgnlUtils;

/**
 * @author qinlei
 * @date 2021/5/31 下午3:48
 */
public class IfSqlNode implements SqlNode {
	/**
	 * 符合OGNL语法的表达式
	 */
	private String test;

	private MixedSqlNode mixedSqlNode;

	public IfSqlNode(String test, MixedSqlNode mixedSqlNode) {
		super();
		this.test = test;
		this.mixedSqlNode = mixedSqlNode;
	}

	@Override
	public void apply(DynamicContext context) {
		// 需要使用到Ognl工具类
		boolean evaluateBoolean = OgnlUtils.evaluateBoolean(test, context.getBindings().get("_parameter"));
		if (evaluateBoolean) {
			mixedSqlNode.apply(context);
		}
	}
}
