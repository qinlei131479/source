package com.course.springtest.reader;

import java.io.InputStream;

import com.course.common.utils.DocumentUtil;
import org.dom4j.Document;

import com.course.springtest.registry.BeanDefinitionRegistry;

/**
 * 对XML进行读取的BeanDefinition阅读器
 * 
 * @author qinlei
 * @date 2021/6/4 下午5:22
 */
public class XmlBeanDefinitionReader {

	private BeanDefinitionRegistry beanDefinitionRegistry;

	public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
		this.beanDefinitionRegistry = beanDefinitionRegistry;
	}

	public void loadBeanDefinitions(InputStream inputStream) {
		Document document = DocumentUtil.createDocument(inputStream);
		XmlBeanDefinitionDocumentReader documentReader = new XmlBeanDefinitionDocumentReader(
				beanDefinitionRegistry);
		documentReader.registerBeanDefinitions(document.getRootElement());
	}
}
