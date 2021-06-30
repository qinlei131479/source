package com.course.mybatis.resource.sqlsession;

import com.course.mybatis.resource.utils.GenericTokenParserUtil;
import com.course.mybatis.resource.handler.ParameterMappingTokenHandler;

/**
 * 将DynamicSqlSource和RawSqlSource中对于#{}的处理抽取到一个类去维护
 * 
 * @author qinlei
 * @date 2021/5/31 下午3:52
 */
public class SqlSourceParser {

	public SqlSource parse(String sqlText) {
		// 将带有#{}的SQL信息进行二次处理，获取#{}中的参数名称和参数类型，最终将解析的参数名称和类型封装到ParameterMapping对象中
		ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
		GenericTokenParserUtil tokenParser = new GenericTokenParserUtil("#{","}");
		String sql = tokenParser.parse(sqlText,tokenHandler);

		return new StaticSqlSource(sql, tokenHandler.getParameterMappings());
	}
}
