package com.course.common.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.course.common.core.entity.CodeName;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import lombok.SneakyThrows;
import ognl.Ognl;
import ognl.OgnlContext;

/**
 * HuTool的二次包装类
 *
 * @author qinlei
 * @date 2020/3/26 下午2:57
 */
public class HuToolUtil {
	/**
	 * 执行urlEncode
	 *
	 * @param src
	 * @return
	 */
	public static String urlEncode(String src) {
		return urlEncode(src, "utf-8");
	}

	/**
	 * 执行urlEncode
	 *
	 * @param src
	 * @return
	 */
	public static String urlEncode(String src, String charset) {
		try {
			return URLEncoder.encode(src, charset);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 枚举类clazz，转化成List<CodeName>
	 *
	 * @param clazz
	 *            枚举类
	 * @return list
	 */
	public static List<CodeName> buildEnumListOfCodeName(Class<?> clazz) {
		return buildEnumList(clazz, "code", "name");
	}

	/**
	 * 枚举类clazz，转化成List<CodeName>，键为字段值，值为字段值
	 *
	 * @param clazz
	 *            枚举类
	 * @param nameField
	 *            字段名，最终调用getXXX方法
	 * @param valueField
	 *            字段名，最终调用getXXX方法
	 * @return list
	 */
	@SneakyThrows
	public static List<CodeName> buildEnumList(Class<?> clazz, String nameField, String valueField) {
		// 得到enum的所有实例
		final Object[] enums = clazz.getEnumConstants();
		if (null == enums) {
			return null;
		}
		final List<CodeName> list = new ArrayList<CodeName>(enums.length);
		for (Object e : enums) {
			list.add(new CodeName(ReflectUtil.getFieldValue(e, nameField), ReflectUtil.getFieldValue(e, valueField)));
		}
		return list;
	}

	/**
	 * 获得枚举名对应指定字段值的Map，键为code，值为name
	 *
	 * @param clazz
	 *            枚举类
	 * @return 枚举名对应指定字段值的Map
	 */
	public static Map<String, Object> buildEnumMapOfCodeName(Class<?> clazz) {
		return buildEnumMap(clazz, "code", "name");
	}

	/**
	 * 获得枚举名对应指定字段值的Map，键为字段值，值为字段值
	 *
	 * @param clazz
	 *            枚举类
	 * @param nameField
	 *            字段名，最终调用getXXX方法
	 * @param valueField
	 *            字段名，最终调用getXXX方法
	 * @return 枚举名对应指定字段值的Map
	 */
	@SneakyThrows
	public static Map<String, Object> buildEnumMap(Class<?> clazz, String nameField, String valueField) {
		// 得到enum的所有实例
		final Object[] enums = clazz.getEnumConstants();
		if (null == enums) {
			return null;
		}
		final Map<String, Object> map = MapUtil.newHashMap(enums.length, true);
		for (Object e : enums) {
			map.put(ReflectUtil.getFieldValue(e, nameField) + "", ReflectUtil.getFieldValue(e, valueField));
		}
		return map;
	}

	/**
	 * 有效邮件正则表达式
	 */
	private static final Pattern PATTERN_EMAIL = Pattern.compile(".+@.+\\..+");
	/**
	 * 手机号正则表达式
	 */
	private static final Pattern PATTERN_MOBILE = Pattern.compile("[0-9]{11}");
	/**
	 * 路径分隔符
	 */
	private static final String PATH_SEREPERATOR = "/";

	/**
	 * 判断是否有效邮件
	 *
	 * @param email
	 */
	public static boolean isValidEmail(String email) {
		if (email == null) {
			return false;
		}
		Matcher m = PATTERN_EMAIL.matcher(email);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否有效手机号
	 *
	 * @param mobile
	 */
	public static boolean isValidMobile(String mobile) {
		Matcher m = PATTERN_MOBILE.matcher(mobile);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 通过ongl表达式获取对象的属性值
	 *
	 * @param obj
	 * @param express
	 * @return
	 */
	public static Object getFieldValueByOgnl(Object obj, String express) {
		OgnlContext context = new OgnlContext();
		context.put("obj", obj);
		// 设置唯一的根对象
		context.setRoot(obj);
		try {
			// 解析字符串，若没有#，则到根对象找。#是明确告诉OGNL从哪个对象中找。调用getName()方法
			Object expressObj = Ognl.parseExpression(express);
			return Ognl.getValue(expressObj, context, context.getRoot());
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * 设置字段值
	 *
	 * @param obj
	 *            对象
	 * @param fieldName
	 *            字段名
	 * @param value
	 *            值，值类型必须与字段类型匹配，不会自动转换对象类型
	 * @throws UtilException
	 *             包装IllegalAccessException异常
	 */
	public static void setFieldValueIfExist(Object obj, String fieldName, Object value) throws UtilException {
		if (obj != null) {
			if (Map.class.isInstance(obj)) {
				// 如果是map
				((Map) obj).put(fieldName, value);
			} else if (ReflectUtil.hasField(obj.getClass(), fieldName)) {
				ReflectUtil.setFieldValue(obj, fieldName, value);
			}
		}
	}

	/**
	 * 获取字段值
	 *
	 * @param obj
	 *            对象
	 * @param fieldName
	 *            字段名
	 * @throws UtilException
	 *             包装IllegalAccessException异常
	 */
	public static Object getFieldValueIfExist(Object obj, String fieldName) throws UtilException {
		if (obj != null && ReflectUtil.hasField(obj.getClass(), fieldName)) {
			return ReflectUtil.getFieldValue(obj, fieldName);
		}
		return null;
	}

	/**
	 * 字符串转换为Double
	 *
	 * @param str
	 *            对象
	 */
	public static Double parseDouble(String str) {
		try {
			if (StrUtil.isNotBlank(str)) {
				return NumberUtil.parseNumber(str).doubleValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串转换为Long
	 *
	 * @param str
	 *            对象
	 */
	public static Long parseLong(Object str) {
		try {
			if (str != null && StrUtil.isNotBlank(str.toString())) {
				return NumberUtil.parseLong(str.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串转换为Integer
	 *
	 * @param str
	 *            对象
	 */
	public static Integer parseInt(Object str) {
		try {
			if (str != null && StrUtil.isNotBlank(str.toString())) {
				return NumberUtil.parseInt(str.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化字符串 ",,,a,b,c，d,，, b e," ==》a,b,c,d,b,e
	 *
	 * @param string
	 * @return
	 */
	public static List<String> convertStringSplitByDouToList(String string) {
		if (StrUtil.isNotBlank(string)) {
			return Arrays.asList(formatStringSplitByDou(string).split(","));
		} else {
			return null;
		}
	}

	/**
	 * 格式化字符串 ",,,a,b,c，d,，, b e," ==》a,b,c,d,b,e
	 *
	 * @param string
	 * @return
	 */
	public static String formatStringSplitByDou(String string) {
		if (StrUtil.isBlank(string)) {
			return string;
		} else {
			String regex = "[　\\s,， ]+";
			return string.trim().replaceAll(regex, ",").replaceAll("^" + regex, "").replaceAll(regex + "$", "");
		}
	}

	/**
	 * 组合路径和文件
	 *
	 * @param dir
	 * @param fileName
	 * @return
	 */
	public static String joinDir(String dir, String fileName) {
		if (fileName.indexOf(PATH_SEREPERATOR) == 0) {
			fileName = fileName.substring(1);
		}
		return dir + (dir.endsWith(PATH_SEREPERATOR) ? "" : PATH_SEREPERATOR) + fileName;
	}

	/**
	 * 组合路径和文件，返回文件格式
	 *
	 * @param dirs
	 * @return
	 */
	public static File joinDirsAsFile(String... dirs) {
		return new File(joinDirs(dirs));
	}

	/**
	 * 组合多个路径部分
	 *
	 * @param dirs
	 * @return
	 */
	public static String joinDirs(String... dirs) {
		if (ArrayUtil.isNotEmpty(dirs)) {
			return joinDirs(Arrays.asList(dirs));
		} else {
			return null;
		}
	}

	/**
	 * 组合多个路径部分
	 *
	 * @param dirs
	 * @return
	 */
	public static String joinDirs(List<String> dirs) {
		if (CollUtil.isNotEmpty(dirs)) {
			String ret = dirs.get(0);
			for (int i = 1; i < dirs.size(); i++) {
				ret = joinDir(ret, dirs.get(i));
			}
			return ret;
		} else {
			return null;
		}
	}

	/**
	 * 获取文件名扩展名（小写,不带点的）
	 *
	 * @param fileName
	 *            :文件名
	 * @return
	 */
	public static String getFileExtName(String fileName) {
		if (fileName == null) {
			return "";
		} else {
			int lastIndex = fileName.lastIndexOf(".");
			return (lastIndex == -1) ? "" : fileName.substring(lastIndex + 1).toLowerCase();
		}
	}

	/**
	 * 执行命令
	 *
	 * @param command
	 *            window:{"cmd.exe /c dir"}
	 * @return
	 * @throws Exception
	 */
	public static String callSystem(String[] command) {
		InputStream is = null;
		try {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			StringBuffer sb = new StringBuffer();
			byte[] buffer = new byte[256];
			int cnt = 0;
			is = process.getInputStream();
			while ((cnt = is.read(buffer)) >= 0) {
				sb.append(new String(buffer, 0, cnt));
			}
			return sb.toString();
		} catch (Throwable e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 执行ognl表达式
	 *
	 * @param expression
	 * @return
	 */
	public static Object eval(String expression) {
		Object obj = null;
		try {
			// 使用 OGNL 表达式来获取值
			obj = Ognl.getValue(expression, null);
		} catch (Exception e) {
			e.printStackTrace();
			obj = null;
		}
		return obj;
	}

	/**
	 * 随机生成密码
	 *
	 * @param pwdLen
	 * @return
	 */
	public static String genRandomPassword(int pwdLen) {
		String str = "abcdefghijklmnopqrstuvwxyz";
		String str1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String str2 = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		int len = pwdLen / 3;
		for (int i = 0; i < len; i++) {
			int number = random.nextInt(26);
			sb.append(str.charAt(number));
			int number1 = random.nextInt(26);
			sb.append(str1.charAt(number1));
			int number2 = random.nextInt(10);
			sb.append(str2.charAt(number2));
		}
		return sb.toString();
	}

	/**
	 * 检查密码是否正确，BCrypt
	 * 
	 * @param plaintext
	 * @param hashed
	 * @return
	 */
	public static boolean checkpw(String plaintext, String hashed) {
		try {
			return BCrypt.checkpw(plaintext, hashed);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 加密密码，BCrypt
	 * 
	 * @param plaintext
	 * @return
	 */
	public static String hashpw(String plaintext) {
		return BCrypt.hashpw(plaintext, BCrypt.gensalt());
	}
}
