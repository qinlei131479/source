package com.course.common.mybatis.resource.factory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import com.course.common.mybatis.resource.config.Configuration;
import com.course.common.mybatis.resource.utils.DocumentUtil;
import org.dom4j.Document;

import com.course.common.mybatis.resource.builder.XMLConfigBuilder;

/**
 * SqlSession工厂构建工具
 * 
 * @author qinlei
 * @date 2021/5/30 下午6:02
 */
public class SqlSessionFactoryBuilder {

	public SqlSessionFactory build(Reader reader) {
		return build(reader, null, null);
	}

	public SqlSessionFactory build(Reader reader, String environment) {
		return build(reader, environment, null);
	}

	public SqlSessionFactory build(Reader reader, Properties properties) {
		return build(reader, null, properties);
	}

	public SqlSessionFactory build(Reader reader, String environment, Properties properties) {
		try {

		} catch (Exception e) {

		} finally {

		}
		return null;
	}

	public SqlSessionFactory build(InputStream inputStream) {
		return build(inputStream, null, null);
	}

	public SqlSessionFactory build(InputStream inputStream, String environment) {
		return build(inputStream, environment, null);
	}

	public SqlSessionFactory build(InputStream inputStream, Properties properties) {
		return build(inputStream, null, properties);
	}

	public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties) {
		Document document = DocumentUtil.createDocument(inputStream);
		XMLConfigBuilder configBuilder = new XMLConfigBuilder();
		Configuration configuration = configBuilder.parseConfiguration(document.getRootElement());

		try {
			inputStream.close();
			return build(configuration);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public SqlSessionFactory build(Configuration config) {
		return new DefaultSqlSessionFactory(config);
	}
}
