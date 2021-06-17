package com.course.springtest.utils;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

/**
 * @author qinlei
 * @date 2021/5/31 下午3:26
 */
public class DocumentUtil {

	public static Document createDocument(InputStream inputStream) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(inputStream);
			return document;
		} catch (Exception e) {
		}
		return null;
	}
}
