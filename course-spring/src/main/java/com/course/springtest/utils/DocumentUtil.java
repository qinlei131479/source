package com.course.springtest.utils;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * Document 文件解析
 * 
 * @author qinlei
 * @date 2021/6/4 下午5:35
 */
public class DocumentUtil {

	/**
	 * 创建Document对象
	 *
	 * @param inputStream
	 * @return
	 */
	public static Document createDocument(InputStream inputStream) {
		Document document = null;
		try {
			SAXReader reader = new SAXReader();
			document = reader.read(inputStream);
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
}
