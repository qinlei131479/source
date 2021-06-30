package com.course.common.mybatis.resource.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author qinlei
 * @date 2021/5/31 下午3:11
 */
public class Resources {

	// private static ClassLoaderWrapper classLoaderWrapper = new
	// ClassLoaderWrapper();

	public static InputStream getResourceAsStream(String resource) throws IOException {
		return Resources.class.getClassLoader().getResourceAsStream(resource);
	}

	public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
		// InputStream in = classLoaderWrapper.getResourceAsStream(resource,
		// loader);
		// if (in == null) {
		// throw new IOException("Could not find resource " + resource);
		// }
		// return in;
		return null;
	}

	public static Properties getResourceAsProperties(String resource) {
		Properties props = new Properties();
		try (InputStream in = getResourceAsStream(resource)) {
			props.load(in);
		} catch (Exception e) {

		}
		return props;
	}
}
