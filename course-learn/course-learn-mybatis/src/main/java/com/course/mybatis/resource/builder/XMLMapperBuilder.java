package com.course.mybatis.resource.builder;

import java.util.List;

import com.course.mybatis.resource.config.Configuration;
import org.dom4j.Element;

/**
 * @author qinlei
 * @date 2021/5/31 下午3:37
 */
public class XMLMapperBuilder {

	private Configuration configuration;

	public XMLMapperBuilder(Configuration configuration) {
		this.configuration = configuration;
	}

	public void parse(Element rootElement) {
		// 为了方便管理statement，需要使用statement唯一标识
		String namespace = rootElement.attributeValue("namespace");

		List<Element> selectElements = rootElement.elements("select");
		for (Element selectElement : selectElements) {

			XMLStatementBuilder statementBuilder = new XMLStatementBuilder(configuration, namespace);
			statementBuilder.parseStatementElement(selectElement);
		}

	}
}
