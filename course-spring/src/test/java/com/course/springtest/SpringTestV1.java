package com.course.springtest;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.course.common.utils.ReflectUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.course.springtest.ioc.BeanDefinition;
import com.course.springtest.ioc.PropertyValue;
import com.course.springtest.ioc.RuntimeBeanReference;
import com.course.springtest.ioc.TypedStringValue;
import com.course.springtest.pojo.User;
import com.course.springtest.service.UserService;

public class SpringTestV1 {

	// K:BeanName
	// V:Bean实例对象
	private Map<String, Object> singletonObjects = new HashMap<String, Object>();
	// K:BeanName
	// V:BeanDefinition对象
	private Map<String, BeanDefinition> beanDefinitions = new HashMap<String, BeanDefinition>();

	@Test
	public void test() {
		// 从XML中加载配置信息，先完成BeanDefinition的注册
		registerBeanDefinitions();

		// 根据用户名称查询用户信息
		UserService userService = (UserService) getBean("userService");

		// 入参对象
		Map<String, Object> param = new HashMap<>();
		param.put("name", "admin");
		// 根据用户名称查询用户信息
		List<User> users = userService.queryUsers(param);
		System.out.println(users);
	}

	public void registerBeanDefinitions() {
		// XML解析，将BeanDefinition注册到beanDefinitions集合中
		String location = "beans.xml";
		// 获取流对象
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(location);
		// 创建文档对象
		Document document = createDocument(inputStream);

		// 按照spring定义的标签语义去解析Document文档
		parseBeanDefinitions(document.getRootElement());
	}

	@SuppressWarnings("unchecked")
	public void parseBeanDefinitions(Element rootElement) {
		// 获取<bean>和自定义标签（比如mvc:interceptors）
		List<Element> elements = rootElement.elements();
		for (Element element : elements) {
			// 获取标签名称
			String name = element.getName();
			if (name.equals("bean")) {
				// 解析默认标签，其实也就是bean标签
				parseDefaultElement(element);
			} else {
				// 解析自定义标签，比如aop:aspect标签
				parseCustomElement(element);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void parseDefaultElement(Element beanElement) {
		try {
			if (beanElement == null)
				return;
			// 获取id属性
			String id = beanElement.attributeValue("id");

			// 获取name属性
			String name = beanElement.attributeValue("name");
			// 获取class属性
			String clazzName = beanElement.attributeValue("class");
			if (clazzName == null || "".equals(clazzName)) {
				return;
			}

			// 获取init-method属性
			String initMethod = beanElement.attributeValue("init-method");
			// 获取scope属性
			String scope = beanElement.attributeValue("scope");
			scope = scope != null && !scope.equals("") ? scope : "singleton";

			// 获取beanName
			String beanName = id == null ? name : id;
			Class<?> clazzType = ReflectUtil.resolveType(clazzName);
			beanName = beanName == null ? clazzType.getSimpleName() : beanName;
			// 创建BeanDefinition对象
			// 此次可以使用构建者模式进行优化
			BeanDefinition beanDefinition = new BeanDefinition(clazzName, beanName);
			beanDefinition.setInitMethod(initMethod);
			beanDefinition.setScope(scope);
			// 获取property子标签集合
			List<Element> propertyElements = beanElement.elements();
			for (Element propertyElement : propertyElements) {
				parsePropertyElement(beanDefinition, propertyElement);
			}

			// 注册BeanDefinition信息
			this.beanDefinitions.put(beanName, beanDefinition);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parsePropertyElement(BeanDefinition beanDefination, Element propertyElement) {
		if (propertyElement == null)
			return;

		// 获取name属性
		String name = propertyElement.attributeValue("name");
		// 获取value属性
		String value = propertyElement.attributeValue("value");
		// 获取ref属性
		String ref = propertyElement.attributeValue("ref");

		// 如果value和ref都有值，则返回
		if (value != null && !value.equals("") && ref != null && !ref.equals("")) {
			return;
		}

		/**
		 * PropertyValue就封装着一个property标签的信息
		 */
		PropertyValue pv = null;

		if (value != null && !value.equals("")) {
			// 因为spring配置文件中的value是String类型，而对象中的属性值是各种各样的，所以需要存储类型
			TypedStringValue typeStringValue = new TypedStringValue(value);

			Class<?> targetType = ReflectUtil.getTypeByFieldName(beanDefination.getClazzName(), name);
			typeStringValue.setTargetType(targetType);

			pv = new PropertyValue(name, typeStringValue);
			beanDefination.addPropertyValue(pv);
		} else if (ref != null && !ref.equals("")) {

			RuntimeBeanReference reference = new RuntimeBeanReference(ref);
			pv = new PropertyValue(name, reference);
			beanDefination.addPropertyValue(pv);
		} else {
			return;
		}
	}

	private void parseCustomElement(Element element) {
		// TODO Auto-generated method stub

	}

	private Document createDocument(InputStream inputStream) {
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

	private Object getBean(String beanName) {
		// 先从一级缓存中获取单例Bean的实例
		Object singletonObject = singletonObjects.get(beanName);

		if (singletonObject != null) {
			return singletonObject;
		}
		// 懒汉式，等你getBean的时候，并且singletonObjects没有该实例的时候，才去创建该实例
		// 当缓存中没有找到该Bean实例，则需要创建Bean，然后将该Bean放入一级缓存中
		// 要创建Bean，需要知道该Bean的信息（这个信息是配置到XML中的）

		// 根据beanName去beanDefinitions获取对应的Bean信息
		BeanDefinition beanDefinition = this.beanDefinitions.get(beanName);
		if (beanDefinition == null) {
			return null;
		}

		// 根据Bean的信息，来判断该bean是单例bean还是多例（原型）bean
		if (beanDefinition.isSingleton()) {
			// 根据Bean的信息去创建Bean的对象
			singletonObject = createBean(beanDefinition);
			// 将Bean的对象，存入到singletonObjects
			this.singletonObjects.put(beanName, singletonObject);
		} else if (beanDefinition.isPrototype()) {
			// 根据Bean的信息去创建Bean的对象
			singletonObject = createBean(beanDefinition);
		} else {
			// TODO 。。。
		}

		return singletonObject;
	}

	private Object createBean(BeanDefinition beanDefinition) {
		Class<?> clazzType = beanDefinition.getClazzType();
		if (clazzType == null) {
			return null;
		}
		// TODO 第一步：new对象（实例化）
		Object bean = createInstanceBean(clazzType);
		if (bean == null) {
			return null;
		}
		// TODO 第二步：依赖注入
		populateBean(bean, beanDefinition);
		// TODO
		// 第三步：初始化，就是调用initMethod指定的初始化方法，或者实现了InitializingBean接口的afterPropertiesSet方法----
		initMethod(bean, beanDefinition);
		return bean;
	}

	private void initMethod(Object bean, BeanDefinition beanDefinition) {
		// TODO 判断Bean是否是实现了Aware接口
		// TODO
		// 判断是否实现了InitilizingBean接口，如果实现，则直接调用该bean的afterPropertiesSet方法去初始化
		// 调用通过bean标签指定的初始化方法，比如通过init-method标签属性指定的方法
		String initMethod = beanDefinition.getInitMethod();
		if (initMethod == null) {
			return;
		}
		invokeInitMethod(bean, initMethod);
	}

	private void invokeInitMethod(Object bean, String initMethod) {
		try {
			Class<?> clazz = bean.getClass();
			Method method = clazz.getDeclaredMethod(initMethod);
			method.invoke(bean);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void populateBean(Object bean, BeanDefinition beanDefinition) {
		List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
		if (propertyValues != null && propertyValues.size() > 0) {
			for (PropertyValue pv : propertyValues) {
				String name = pv.getName();
				Object value = pv.getValue();

				Object valueToUse = null;
				if (value instanceof TypedStringValue) {
					TypedStringValue typedStringValue = (TypedStringValue) value;
					String stringValue = typedStringValue.getValue();
					Class<?> targetType = typedStringValue.getTargetType();

					// TODO 通过策略模式去优化
					// typedStringValue.getTypeHandler();
					if (targetType == Integer.class) {
						valueToUse = Integer.parseInt(stringValue);
					} else if (targetType == String.class) {
						valueToUse = stringValue;
					}
				} else if (value instanceof RuntimeBeanReference) {
					RuntimeBeanReference beanReference = (RuntimeBeanReference) value;
					String ref = beanReference.getRef();
					valueToUse = getBean(ref);
				}

				// 通过反射去给bean实例去设置指定name的值
				setProperty(bean, name, valueToUse);
			}
		}

	}

	private void setProperty(Object bean, String name, Object valueToUse) {
		try {
			Class<?> clazz = bean.getClass();
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);

			field.set(bean, valueToUse);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Object createInstanceBean(Class<?> clazzType) {
		// TODO 通过实例工厂方式去创建Bean实例，比如通过factory-bean标签属性指的FactoryBean工厂去创建实例
		// TODO 通过静态工厂方法方式去创建Bean实例，比如通过factory-method标签属性指的静态工厂方法去创建实例

		// 构造方法去创建Bean实例（此处我们只针对无参构造进行操作）
		Object bean = doCreateInstanceWithConstructor(clazzType);

		return bean;
	}

	private Object doCreateInstanceWithConstructor(Class<?> clazzType) {
		try {
			// TODO 获取所有的构造方法
			// TODO 根据BeanDefinition中存储的constructor-arg子标签封装的数据来获取构造参数类型
			Constructor<?> constructor = clazzType.getDeclaredConstructor();
			return constructor.newInstance();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
