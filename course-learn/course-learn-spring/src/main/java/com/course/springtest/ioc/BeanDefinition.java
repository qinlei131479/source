package com.course.springtest.ioc;

import java.util.ArrayList;
import java.util.List;


import com.course.springtest.utils.ReflectUtil;
import lombok.Data;

/**
 * @author qinlei
 * @date 2021/6/4 下午4:57
 */
@Data
public class BeanDefinition {

	private static final String SCOPE_SINGLETON = "singleton";
	private static final String SCOPE_PROTOTYPE = "prototype";
	/**
	 * bean标签的class属性
	 */
	private String clazzName;
	/**
	 * bean标签的class属性对应的Class对象
	 */
	private Class<?> clazzType;
	/**
	 * bean标签的id属性和name属性都会存储到该属性值，id和name属性是二选一使用的
	 */
	private String beanName;
	private String initMethod;
	/**
	 * 该信息是默认的配置，如果不配置就默认是singleton
	 */
	private String scope;
	/**
	 * bean中的属性信息
	 */
	private List<PropertyValue> propertyValues;

	public BeanDefinition(String clazzName, String beanName) {
		this.beanName = beanName;
		this.clazzName = clazzName;
		this.clazzType = ReflectUtil.resolveType(clazzName);
	}

	public void addPropertyValue(PropertyValue propertyValue) {
		if (this.propertyValues == null) {
			this.propertyValues = new ArrayList<>();
		}
		this.propertyValues.add(propertyValue);
	}

	public boolean isSingleton() {
		return SCOPE_SINGLETON.equals(this.scope);
	}

	public boolean isPrototype() {
		return SCOPE_PROTOTYPE.equals(this.scope);
	}

}
