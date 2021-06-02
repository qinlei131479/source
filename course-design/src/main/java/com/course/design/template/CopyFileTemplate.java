package com.course.design.template;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author qinlei
 * @date 2021/6/2 下午6:21
 */
public class CopyFileTemplate extends GetTimeTemplate {

	@Override
	public void code() {
		// 复制文件
		try {
			String path = "/Users/qinlei/Downloads/apicture/";
			BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path + "points-rule.png"));

			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path + "mn.jpg"));

			byte[] bs = new byte[256];
			int len = 0;

			while ((len = inputStream.read(bs)) != -1) {
				outputStream.write(bs, 0, len);
				outputStream.flush();
			}
			// 释放资源
			inputStream.close();
			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
