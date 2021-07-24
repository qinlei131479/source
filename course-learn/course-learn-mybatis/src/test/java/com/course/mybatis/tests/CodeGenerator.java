package com.course.mybatis.tests;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

	public static void main(String[] args) {

		//////////// 使用代码生成器 ////////////
		AutoGenerator mpg = new AutoGenerator();

		//////////// 使用全局配置 ////////////
		GlobalConfig gc = new GlobalConfig();
		// 获取项目路径
		String projectPath = System.getProperty("user.dir") + "/course-mybatisplus";
		// 生成文件的输出目录
		gc.setOutputDir(projectPath + "/src/main/java");
		// 作者
		gc.setAuthor("qinlei");
		// 开启 swagger2 模式
		// gc.setSwagger2(true);
		// 是否打开输出目录
		gc.setOpen(false);
		// 指定生成的主键的ID类型
		gc.setIdType(IdType.AUTO);
		// 如果有同名文件，是否覆盖
		gc.setFileOverride(true);
		// 设置给到代码生成器
		mpg.setGlobalConfig(gc);

		//////////// 数据源配置 ////////////
		DataSourceConfig dsc = new DataSourceConfig();
		// 选择使用 MySQL
		dsc.setDbType(DbType.MYSQL);
		dsc.setUrl("jdbc:mysql://localhost:3306/swine_main?useUnicode=true&useSSL=false&characterEncoding=utf8");
		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername("root");
		dsc.setPassword("iflash");
		mpg.setDataSource(dsc);

		//////////// 包配置 ////////////
		PackageConfig pc = new PackageConfig();
		pc.setParent("com.course.mybatis.plus");
		mpg.setPackageInfo(pc);

		//////////// 自定义输出配置 ////////////
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
		};
		// 如果模板引擎是 freemarker
		// String templatePath = "/templates/mapper.xml.ftl";
		// 如果模板引擎是 velocity
		String templatePath = "/templates/mapper.xml.vm";

		List<FileOutConfig> focList = new ArrayList<>();
		// 自定义配置会被优先输出
		focList.add(new FileOutConfig(templatePath) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
				return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper"
						+ StringPool.DOT_XML;
			}
		});
		cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);
		// 配置模板
		TemplateConfig templateConfig = new TemplateConfig();
		templateConfig.setXml(null);
		mpg.setTemplate(templateConfig);

		//////////// 策略配置 ////////////
		StrategyConfig strategy = new StrategyConfig();
		// 表名（驼峰命名）
		strategy.setNaming(NamingStrategy.underline_to_camel);
		// 设置表前缀
		// strategy.setTablePrefix(pc.getModuleName() + "_");
		strategy.setTablePrefix("sys_");
		// 字段命名
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		// 【实体】是否为lombok模型（默认 false）
		strategy.setEntityLombokModel(true);
		// 生成 <code>@RestController</code> 控制器
		strategy.setRestControllerStyle(true);
		mpg.setStrategy(strategy);

		mpg.execute();
	}
}
