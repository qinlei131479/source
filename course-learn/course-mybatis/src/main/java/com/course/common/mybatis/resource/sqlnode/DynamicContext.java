package com.course.common.mybatis.resource.sqlnode;

import java.util.HashMap;
import java.util.Map;

/**
 * SqlNode处理过程中的动态上下文
 * 
 * @author qinlei
 * @date 2021/5/31 下午4:03
 */
public class DynamicContext {

	private StringBuffer sb = new StringBuffer();

	private Map<String, Object> bindings = new HashMap<String, Object>();

	public DynamicContext(Object paramObject) {
		bindings.put("_parameter", paramObject);
	}

	public void appendSql(String sqlText) {
		sb.append(sqlText);
		sb.append(" ");
	}

	public String getSql() {
		return sb.toString();
	}

	public Map<String, Object> getBindings() {
		return bindings;
	}
}
