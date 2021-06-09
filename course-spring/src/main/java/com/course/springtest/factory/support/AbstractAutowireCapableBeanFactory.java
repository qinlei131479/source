package com.course.springtest.factory.support;

import java.util.List;

import com.course.springtest.aware.Aware;
import com.course.springtest.aware.BeanFactoryAware;
import com.course.springtest.ioc.BeanDefinition;
import com.course.springtest.ioc.PropertyValue;
import com.course.springtest.ioc.RuntimeBeanReference;
import com.course.springtest.ioc.TypedStringValue;
import com.course.springtest.utils.ReflectUtils;

/**
 * 默认自动解析初始化bean实现
 *
 * @author qinlei
 * @date 2021/6/4 下午5:58
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

	@Override
	protected Object createBean(BeanDefinition beanDefinition) {
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

	/**
	 * 创建bean: 1、实例化bean
	 *
	 * @param clazzType
	 * @return
	 */
	private Object createInstanceBean(Class<?> clazzType) {
		// TODO 通过实例工厂方式去创建Bean实例，比如通过factory-bean标签属性指的FactoryBean工厂去创建实例
		// TODO 通过静态工厂方法方式去创建Bean实例，比如通过factory-method标签属性指的静态工厂方法去创建实例

		// 构造方法去创建Bean实例（此处我们只针对无参构造进行操作）
		Object bean = ReflectUtils.createObject(clazzType);

		return bean;
	}

	/**
	 * 创建bean: 2、填充属性值
	 *
	 * @param bean
	 * @param beanDefinition
	 */
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
				ReflectUtils.setProperty(bean, name, valueToUse);
			}
		}
	}

	/**
	 * 创建bean: 3、执行初始化方法
	 * 
	 * @param bean
	 * @param beanDefinition
	 */
	private void initMethod(Object bean, BeanDefinition beanDefinition) {
		// TODO 判断Bean是否是实现了Aware接口
		if (bean instanceof Aware) {
			if (bean instanceof BeanFactoryAware) {
				((BeanFactoryAware) bean).setBeanFactory(this);
			}
		}
		// TODO
		// 判断是否实现了InitilizingBean接口，如果实现，则直接调用该bean的afterPropertiesSet方法去初始化
		// 调用通过bean标签指定的初始化方法，比如通过init-method标签属性指定的方法
		String initMethod = beanDefinition.getInitMethod();
		if (initMethod == null) {
			return;
		}
		ReflectUtils.invokeMethod(bean, initMethod);
	}

	@Override
	protected BeanDefinition getBeanDefinition(String beanName) {
		return null;// this.singletonObjects.get(beanName);
	}

}
