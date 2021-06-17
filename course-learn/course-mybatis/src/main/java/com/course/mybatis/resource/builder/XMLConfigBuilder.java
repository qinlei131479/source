package com.course.mybatis.resource.builder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.course.mybatis.resource.config.Configuration;
import com.course.mybatis.resource.io.Resources;
import com.course.mybatis.resource.utils.GenericTokenParserUtil;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import com.course.common.utils.DocumentUtil;

/**
 * XML Config构建工具
 * 
 * @author qinlei
 * @date 2021/5/30 下午6:14
 */
public class XMLConfigBuilder {

	private Configuration configuration;
	private String environment;
	private Properties props;
	private GenericTokenParserUtil tokenParserUtil;

	public XMLConfigBuilder() {
		this.configuration = new Configuration();
		tokenParserUtil = new GenericTokenParserUtil("${", "}");
	}

	public XMLConfigBuilder(InputStream inputStream, String environment, Properties props) throws IOException {
		Document document = DocumentUtil.createDocument(inputStream);
		this.configuration = parseConfiguration(document.getRootElement());
		this.environment = environment;
		this.props = props;
	}

	public Configuration getConfiguration() {
		return this.configuration;
	}

	public Configuration parseConfiguration(Element rootElement) {
		Element properties = rootElement.element("properties");
		parseProperties(properties);

		Element environments = rootElement.element("environments");
		parseEnvironments(environments);

		Element mappers = rootElement.element("mappers");
		parseMappers(mappers);
		return configuration;
	}

	private void parseProperties(Element propertiesElement) {
		if (propertiesElement != null) {
			String resource = propertiesElement.attributeValue("resource");
			Properties props = Resources.getResourceAsProperties(resource);
			this.configuration.setVariables(props);
		}
	}

	private void parseEnvironments(Element environments) {
		String def = environments.attributeValue("default");
		List<Element> envList = environments.elements();
		for (Element element : envList) {
			String id = element.attributeValue("id");
			if (id.equals(def)) {
				parseDataSource(element);
			}
		}
	}

	private void parseMappers(Element mappers) {
		List<Element> mapperElements = mappers.elements("mapper");
		for (Element mapperElement : mapperElements) {
			String resource = mapperElement.attributeValue("resource");

			InputStream inputStream = null;
			try {
				inputStream = Resources.getResourceAsStream(resource);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Document document = DocumentUtil.createDocument(inputStream);
			// 按照映射文件的语义进行解析
			XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(configuration);
			mapperBuilder.parse(document.getRootElement());
		}
	}

	private void parseDataSource(Element element) {
		Element dataSourceElement = element.element("dataSource");
		String type = dataSourceElement.attributeValue("type");
		if (type.equals("POOLED")) {
			BasicDataSource dataSource = new BasicDataSource();

			Properties properties = parseProperty(dataSourceElement);

			dataSource.setDriverClassName(replaceProperties(properties.getProperty("driver")));
			dataSource.setUrl(replaceProperties(properties.getProperty("url")));
			dataSource.setUsername(replaceProperties(properties.getProperty("username")));
			dataSource.setPassword(replaceProperties(properties.getProperty("password")));

			configuration.setDataSource(dataSource);
		}
	}

	private Properties parseProperty(Element dataSourceElement) {
		Properties properties = new Properties();

		List<Element> propElements = dataSourceElement.elements("property");
		for (Element propElement : propElements) {
			String name = propElement.attributeValue("name");
			String value = propElement.attributeValue("value");
			properties.put(name, value);
		}
		return properties;
	}

	/**
	 * 配置属性替换
	 * 
	 * @param properties
	 * @return
	 */
	private String replaceProperties(String properties) {
		if (properties != null) {
			return tokenParserUtil.parse(properties, configuration.getVariables());
		}
		return properties;
	}
}
