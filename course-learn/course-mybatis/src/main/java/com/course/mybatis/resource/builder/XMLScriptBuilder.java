package com.course.mybatis.resource.builder;

import java.util.ArrayList;
import java.util.List;

import com.course.mybatis.resource.sqlsession.DynamicSqlSource;
import com.course.mybatis.resource.sqlsession.RawSqlSource;
import com.course.mybatis.resource.sqlsession.SqlSource;
import com.course.mybatis.resource.sqlnode.*;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

/**
 * @author qinlei
 * @date 2021/5/31 下午3:41
 */
public class XMLScriptBuilder {

	private boolean isDynamic;

	public SqlSource parseScriptNode(Element selectElement) {
		// 解析select标签下的所有SQL脚本信息，比如sql文本片段、动态标签等，最终封装成SqlNode集合
		MixedSqlNode rootSqlNode = parseDynamicTags(selectElement);
		// 在解析SqlNode的时候，我们需要知道这些Sql文本中是否包含${}，或者sql脚本中是否包含动态标签
		// 包含${}和动态标签的这种SQL信息，我们封装到DynamicSqlSource
		SqlSource sqlSource = null;
		if (isDynamic) {
			sqlSource = new DynamicSqlSource(rootSqlNode);
		} else {
			sqlSource = new RawSqlSource(rootSqlNode);
		}
		// 如果整个SQL信息中只包含#{}或者说什么特殊字符都没有，那么我们将这些SQL信息封装到RawSqlSource

		return sqlSource;
	}

	private MixedSqlNode parseDynamicTags(Element selectElement) {
		List<SqlNode> sqlNodes = new ArrayList<>();
		// 获取select并且的子节点
		int nodeCount = selectElement.nodeCount();
		// 遍历所有节点
		for (int i = 0; i < nodeCount; i++) {
			Node node = selectElement.node(i);
			// 判断节点是文本节点还是Element节点，如果是文本节点
			if (node instanceof Text) {
				// 获取SQL文本
				String sqlText = node.getText().trim();
				if (sqlText == null || "".equals(sqlText)) {
					continue;
				}
				// 判断文本节点中是否包含${}
				TextSqlNode textSqlNode = new TextSqlNode(sqlText);
				// 如果包含${}，那么将文本节点的SQL文本封装到TextSqlNode
				if (textSqlNode.isDynamic()) {
					sqlNodes.add(textSqlNode);
					isDynamic = true;
				} else {
					// 如果不包含${}，那么将文本节点的SQL文本封装到StaticTextSqlNode
					sqlNodes.add(new StaticTextSqlNode(sqlText));
				}

			} else if (node instanceof Element) {
				// 如果是Element节点，那么需要获取节点名称
				Element element = (Element) node;
				String name = element.getName();

				// TODO 此处使用策略模式去优化

				// 根据名称创建对应Element的SqlNode对象，封装对应Element的节点数据，比如if标签对应IfSqlNode
				if ("if".equals(name)) {
					// 获取判断表达式
					String test = element.attributeValue("test");
					// 递归给if标签动态解析，获取它的子标签SqlNode节点集合
					MixedSqlNode mixedSqlNode = parseDynamicTags(element);

					IfSqlNode ifSqlNode = new IfSqlNode(test, mixedSqlNode);
					sqlNodes.add(ifSqlNode);
				} else if ("where".equals(name)) {
					// TODO 。。。。
				}
			}
		}
		return new MixedSqlNode(sqlNodes);
	}
}
