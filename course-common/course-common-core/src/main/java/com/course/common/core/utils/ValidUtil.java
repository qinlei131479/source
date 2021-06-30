package com.course.common.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import javax.validation.Constraint;

import com.course.common.core.annotation.ValidExpress;
import com.course.common.core.entity.Valid;
import com.course.common.core.entity.ValidArg;
import com.course.common.core.entity.ValidItem;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.validation.annotation.Validated;

import com.course.common.core.entity.Req;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qinlei
 * @date 2021/6/24 下午5:15
 */
@Slf4j
public class ValidUtil {

	/**
	 * 2个参数的函数接口，通过注解获取aop的注解和对应的参数
	 */
	public static BiFunction<ProceedingJoinPoint, Class, ValidArg> getArgAndAnnotation = (point, c) -> {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
		if (parameterAnnotations != null) {
			for (int i = 0; i < parameterAnnotations.length; i++) {
				Annotation[] annotations = parameterAnnotations[i];
				if (annotations != null) {
					for (Annotation annotation : annotations) {
						if (annotation.annotationType().getName().equals(c.getName())) {
							ValidArg arg = new ValidArg();
							arg.setAnnotation(annotation);
							arg.setArg(point.getArgs()[i]);
							return arg;
						}
					}
				}
			}
		}
		return null;
	};

	/**
	 * 通过类型获取参数对象
	 * 
	 * @param point
	 * @param clazz
	 * @param <C>
	 * @return
	 */
	public static <C> C getArgByClass(ProceedingJoinPoint point, Class<C> clazz) {
		for (Object arg : point.getArgs()) {
			if (clazz.isInstance(arg)) {
				return (C) arg;
			}
		}
		return null;
	}

	/**
	 * 获取验证List
	 * 
	 * @param point
	 * @param req
	 * @return
	 */
	public static List<Valid> handleValidList(ProceedingJoinPoint point, Req req) {
		ValidArg arg = ValidUtil.getArgAndAnnotation.apply(point, Validated.class);
		if (arg != null) {
			InvocationHandler handler = Proxy.getInvocationHandler(arg.getAnnotation());
			Map<String, Object> map = (Map<String, Object>) ReflectUtil.getFieldValue(handler, "memberValues");
			Object value = map.get("value");
			if (value != null && value.getClass().isArray()) {
				int length = Array.getLength(value);
				if (length > 0) {
					Object aItem = Array.get(value, 0);
					return buildValidList(null, req.getClass(), (Class) aItem);
				}
			}
		}
		return null;
	}

	/**
	 * 构建验证数据
	 * 
	 * @param parentField
	 * @param entity
	 * @param group
	 * @return
	 */
	public static List<Valid> buildValidList(String parentField, Class entity, Class group) {
		Field[] fields = entity.getDeclaredFields();
		List<Valid> ret = CollUtil.newArrayList();
		for (int i = 0; i < fields.length; i++) {
			Annotation[] as = fields[i].getAnnotations();
			List<ValidItem> validItemList = CollUtil.newArrayList();
			for (Annotation a : as) {
				InvocationHandler handler = Proxy.getInvocationHandler(a);
				Map<String, Object> map = (Map<String, Object>) ReflectUtil.getFieldValue(handler, "memberValues");
				Object value = map.get("value");
				if (value != null && value.getClass().isArray()) {
					// 一个字段，多个验证（多次同样类型的验证，但group不同）
					int length = Array.getLength(value);
					for (int j = 0; j < length; j++) {
						Object aItem = Array.get(value, j);
						if (isAnnotation((Annotation) aItem, Constraint.class)) {
							InvocationHandler handlerSub = Proxy.getInvocationHandler(aItem);
							Map<String, Object> mapSub = (Map<String, Object>) ReflectUtil.getFieldValue(handlerSub,
									"memberValues");
							ValidItem item = buildValidItem(mapSub, group, handlerSub);
							if (item != null) {
								validItemList.add(item);
							}
						}
					}
				} else {
					if (isAnnotation(a, Constraint.class)) {
						// 一个字段单个验证
						ValidItem item = buildValidItem(map, group, handler);
						if (item != null) {
							validItemList.add(item);
						}
					} else if (a.annotationType().getName().equals(javax.validation.Valid.class.getName())) {
						// 子属性有Valid
						ret.addAll(buildValidList(fields[i].getName() + ".", fields[i].getType(), group));
					}
				}
			}
			if (CollUtil.isNotEmpty(validItemList)) {
				String pre = StrUtil.isNotBlank(parentField) ? parentField : "";
				ret.add(new Valid(pre + fields[i].getName(), validItemList));
			}
		}
		return ret;
	}

	/**
	 * 判断注释是否为特定类型的注解(单个）
	 * 
	 * @param a
	 * @param clazz
	 * @return
	 */
	private static boolean isAnnotation(Annotation a, Class clazz) {
		if (ArrayUtil.isNotEmpty(a.annotationType().getAnnotations())) {
			for (Annotation b : a.annotationType().getAnnotations()) {
				if (b.annotationType().getName().equals(clazz.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 构建单个验证
	 * 
	 * @param map
	 * @param group
	 * @param handler
	 * @return
	 */
	private static ValidItem buildValidItem(Map<String, Object> map, Class group, InvocationHandler handler) {
		Map<String, Object> map2 = MapUtil.newHashMap(true);
		if (CollUtil.isNotEmpty(map)) {
			for (String key : map.keySet()) {
				if (!key.equals("groups") && !key.equals("payload")) {
					map2.put(key, map.get(key));
				}
			}
		}
		Class[] groups = (Class[]) map.get("groups");
		if (ArrayUtil.contains(groups, group)) {
			Object type = ReflectUtil.getFieldValue(handler, "type");
			String name = ((Class) type).getSimpleName();
			ValidItem item = new ValidItem(name, map2);
			return item;
		}
		return null;
	}

	/**
	 * 判断表达式是否有效
	 * 
	 * @param bean
	 * @param field
	 * @param validatedGroupClazzs
	 * @return
	 */
	public static boolean checkValidEnable(Object bean, String field, Class<?>[] validatedGroupClazzs) {
		// customerList[0].maxLevel、reserve.roomName、address
		String[] paths = field.split("[.]");
		List<String> pathList = Arrays.asList(paths);
		for (int i = 0; i < paths.length; i++) {
			Object node = bean;
			if (i > 0) {
				node = HuToolUtil.getFieldValueByOgnl(bean, CollUtil.join(pathList.subList(0, i), "."));
			}
			String subFieldName = paths[i].replaceAll("\\[[0-9]*\\]", "");
			Field subField = ReflectUtil.getField(node.getClass(), subFieldName);
			if (subField != null) {
				ValidExpress ve = subField.getAnnotation(ValidExpress.class);
				if (ve != null) {
					if (validatedGroupClazzs == null) {
						return true;
					}
					// group是否匹配：controller的validate.group，包含在ValidExpress的groups中
					if (ArrayUtil.isNotEmpty(ve.groups())) {
						boolean groupMatch = false;
						for (Class validatedGroupClazz : validatedGroupClazzs) {
							if (ArrayUtil.contains(ve.groups(), validatedGroupClazz)) {
								groupMatch = true;
								break;
							}
						}
						// group不匹配，直接返回true
						if (groupMatch == false) {
							return true;
						}
					}
					Object fieldValue = HuToolUtil.getFieldValueIfExist(node, ve.enableField());
					boolean needCheck = fieldValue != null && ArrayUtil.contains(ve.enableValues(), fieldValue + "");
					if (needCheck == false) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
