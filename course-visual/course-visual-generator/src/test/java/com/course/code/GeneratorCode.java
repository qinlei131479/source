package com.course.code;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.course.common.core.utils.FreemarkerUtil;
import com.course.common.core.utils.HuToolUtil;
import com.course.generator.GeneratorCodeApplication;
import com.course.generator.entity.TableField;
import com.course.generator.mapper.TableFieldMapper;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * 代码自动生成器<br>
 * 1、配置application数据库环境<br>
 * 2、执行#testCreateAll方法，根据项目需求更改packageName、packageName_entity等字段<br>
 * 3、到目标模块验证代码的生成<br>
 * 
 * @author qinlei
 * @date 2021/6/11 下午1:12
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GeneratorCodeApplication.class)
public class GeneratorCode {

	// 全局配置
	public static String dbName = "swine_main";
	public static String templatePath = "classpath:/ftl/code/";
	public static String javaTopFilePath = "/src/main/java/";
	public static String resourcesFilePath = "/src/main/resources/";
	public static String currentParentPath = new File("").getAbsolutePath().replace("course-visual/course-generator",
			"");
	/**
	 * 公共包路径,参考ftl模板配置
	 */
	public static String packageName_core = "com.course.common.core";
	public static String packageName_mybatis_plus = "com.course.common.mybatis";
	public static Map<String, Object> dataMap = new HashMap<String, Object>();

	static {
		dataMap.put("package_core", packageName_core);
		dataMap.put("package_mybatisplus", packageName_mybatis_plus);
		dataMap.put("author", "qinlei");
	}

	@Resource
	private TableFieldMapper tableFieldMapper;

	/**
	 * 表结构查询
	 * 
	 * @param tableName_pre
	 * @param className
	 * @return
	 */
	private Map<String, Object> beforeQuery(String tableName_pre, String className) {
		Map<String, Object> beforeMap = new HashMap<String, Object>();
		beforeMap.put("tableName_pre", tableName_pre);
		beforeMap.put("className", className);

		TableField dto = new TableField();
		dto.setTableName_pre(tableName_pre);
		dto.setDbName(dbName);
		dto.setClassName(className);
		if (tableFieldMapper != null) {
			List<TableField> ret = tableFieldMapper.findTableFieldListByTableName(dto);
			beforeMap.put("tableFields", ret);
			String tableComment = tableFieldMapper.findTableComment(dto);
			beforeMap.put("tableComment", tableComment);
		}
		return beforeMap;
	}

	@Before
	public void before() throws Exception {

	}

	/**
	 * 生成模板目标文件
	 * 
	 * @param basePath
	 * @param packagePath
	 * @param subPath
	 * @param fileName
	 * @throws Exception
	 */
	private void writeJavaContent(String basePath, String packagePath, String subPath, String fileName,
			Map<String, Object> dataMap) throws Exception {
		writeJavaContent(basePath, packagePath, subPath, null, fileName, dataMap);
	}

	/**
	 * 生成模板目标文件
	 * 
	 * @param basePath
	 * @param packagePath
	 * @param subPath
	 * @param ftlName
	 * @param fileName
	 * @throws Exception
	 */
	private void writeJavaContent(String basePath, String packagePath, String subPath, String ftlName, String fileName,
			Map<String, Object> classMap) throws Exception {
		String ftlNameNew = StrUtil.isBlank(ftlName) ? subPath : ftlName;
		Map<String, Object> resultMap = classMap == null ? new HashMap<>() : classMap;
		resultMap.putAll(dataMap);
		String content = FreemarkerUtil.getTextWithTemplate(templatePath, ftlNameNew + ".txt", "UTF-8", resultMap);
		FileUtil.writeUtf8String(content, HuToolUtil.joinDirs(basePath, packagePath, subPath, fileName));
	}

	@Test
	public void testCreateAll() throws Exception {
		// 生成目标包路径
		String packageName = "com.course.springboot";
		// 生成目标实体包路径
		String packageName_entity = "com.course.springboot";
		// 数据库表前缀
		String tableName_pre = "sys_";
		// 表名称
		List<String> classList = Arrays.asList("Role");
		// 添加包和实体包
		dataMap.put("package", packageName);
		dataMap.put("package_entity", packageName_entity);
		// 有时候实体一层会分离分子项目
		String basePath = currentParentPath + "course-learn/course-springboot";
		String basePath_entity = currentParentPath + "course-learn/course-springboot";
		String javaFilePath = javaTopFilePath + packageName.replaceAll("\\.", "/");
		String javaFilePath_entity = javaTopFilePath + packageName_entity.replaceAll("\\.", "/");

		classList.forEach(className -> {
			Map<String, Object> dataMap = beforeQuery(tableName_pre, className);
			try {
				// entity
				writeJavaContent(basePath_entity, javaFilePath_entity, "entity", className + ".java", dataMap);
				// mapper and mapper.xml
				writeJavaContent(basePath, javaFilePath, "mapper", className + "Mapper.java", dataMap);
				writeJavaContent(basePath, resourcesFilePath, "mapper", "mapperxml", className + "Mapper.xml", dataMap);
				// service and serviceImpl
				writeJavaContent(basePath, javaFilePath, "service", className + "Service.java", dataMap);
				writeJavaContent(basePath, javaFilePath, "service/impl", "serviceimpl", className + "ServiceImpl.java",
						dataMap);
				// controller
				writeJavaContent(basePath, javaFilePath, "controller", className + "Controller.java", dataMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
