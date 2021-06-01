package com.course.mybatis.resource.sqlnode;

import com.course.mybatis.resource.utils.GenericTokenParserUtil;
import com.course.mybatis.resource.utils.OgnlUtils;
import com.course.mybatis.resource.utils.SimpleTypeRegistry;
import com.course.mybatis.resource.utils.TokenHandler;

/**
 * 封装带有${}的SQL文本
 * 
 * @author qinlei
 * @date 2021/5/31 下午4:14
 */
public class TextSqlNode implements SqlNode {

	private String sqlText;

	public TextSqlNode(String sqlText) {
		this.sqlText = sqlText;
	}

	@Override
	public void apply(DynamicContext context) {
		// 处理${}中被分词出来的名称，比如user.username
		BindingTokenHandler tokenHandler = new BindingTokenHandler(context);

		GenericTokenParserUtil tokenParser = new GenericTokenParserUtil("${","}");
		String sql = tokenParser.parse(sqlText,tokenHandler);
		context.appendSql(sql);
	}

	/**
	 * 封装SqlText的对象最了解如何对它进行判断
	 *
	 * @return
	 */
	public boolean isDynamic() {
		if (sqlText.indexOf("${") > -1) {
			return true;
		}
		return false;
	}

	private static class BindingTokenHandler implements TokenHandler {

		private DynamicContext context;

		public BindingTokenHandler(DynamicContext context) {
			this.context = context;
		}

		/**
		 * content：就是${}中的名字，比如${user.username},那么content就是user.username
		 */
		@Override
		public String handleToken(String content) {
			Object paramObject = context.getBindings().get("_parameter");
			// 如果入参对象为null，则返回""
			if (paramObject == null) {
				return "";
			} else if (SimpleTypeRegistry.isSimpleType(paramObject.getClass())) {
				// 如果入参对象是简单类型或者String，则直接根据字符串转换成指定的类型
				return paramObject.toString();
			}
			// 如果是自定义的Java对象，则需要使用OGNL去入参对象中获取值，然后返回
			Object value = OgnlUtils.getValue(content, paramObject);
			return value.toString();
		}

	}
}
