package com.course.mybatis.resource.builder;

import java.util.Locale;

import com.course.mybatis.resource.config.Configuration;
import com.course.mybatis.resource.config.MappedStatement;
import com.course.mybatis.resource.sqlsession.SqlSource;
import com.course.mybatis.resource.utils.ReflectUtil;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.dom4j.Element;


/**
 * 专门用来解析select/insert/delete/update这些statement标签的
 * 
 * @author qinlei
 * @date 2021/5/31 下午3:38
 */
class XMLStatementBuilder {

	private String namespace;
	private Configuration configuration;
	private final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

	public XMLStatementBuilder(Configuration configuration, String namespace) {
		this.configuration = configuration;
		this.namespace = namespace;
	}

	public void parseStatementElement(Element selectElement) {
		String statementId = selectElement.attributeValue("id");

		if (statementId == null || statementId.equals("")) {
			return;
		}
		// 一个CURD标签对应一个MappedStatement对象
		// 一个MappedStatement对象由一个statementId来标识，所以保证唯一性
		// statementId = namespace + "." + CRUD标签的id属性
		statementId = namespace + "." + statementId;

		String parameterType = selectElement.attributeValue("parameterType");
		Class<?> parameterClass = getClass(parameterType);
		String resultType = selectElement.attributeValue("resultType");
		Class<?> resultClass = getClass(resultType);

		String statementType = selectElement.attributeValue("statementType");
		statementType = statementType == null || statementType == "" ? "prepared" : statementType;

		// SqlSource的封装过程
		SqlSource sqlSource = createSqlSource(selectElement);

		// TODO 建议使用构建者模式去优化
		MappedStatement mappedStatement = new MappedStatement(statementId, parameterClass, resultClass, statementType,
				sqlSource);
		configuration.addMappedStatement(statementId, mappedStatement);

	}

	private SqlSource createSqlSource(Element selectElement) {
		XMLScriptBuilder scriptBuilder = new XMLScriptBuilder();
		SqlSource sqlSource = scriptBuilder.parseScriptNode(selectElement);
		return sqlSource;
	}

	/**
	 * 参数类型检查并转换
	 * 
	 * @param type
	 * @return
	 */
	private Class<?> getClass(String type) {
		String key = type.toLowerCase(Locale.ENGLISH);
		Class<?> resultClass;
		if (typeAliasRegistry.getTypeAliases().containsKey(key)) {
			resultClass = typeAliasRegistry.getTypeAliases().get(key);
		} else {
			resultClass = ReflectUtil.resolveType(type);
		}
		return resultClass;
	}
}
