package com.course.common.mybatis.resource.sqlnode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinlei
 * @date 2021/5/31 下午4:04
 */
public class MixedSqlNode implements SqlNode {

	/**
	 * 解析的SqlNode集合信息
	 */
	private List<SqlNode> sqlNodes = new ArrayList<SqlNode>();

	public MixedSqlNode(List<SqlNode> sqlNodes) {
		super();
		this.sqlNodes = sqlNodes;
	}

	@Override
	public void apply(DynamicContext context) {
		for (SqlNode sqlNode : sqlNodes) {
			sqlNode.apply(context);
		}
	}
}
