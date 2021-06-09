package com.course.mvc.adapter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.course.common.utils.JsonUtil;
import com.course.mvc.adapter.face.HandlerAdapter;
import com.course.mvc.annotations.ResponseBody;
import com.course.mvc.handler.HandlerMethod;

/**
 * HttpRequestHandler的适配器
 * 
 * @author qinlei
 * @date 2021/6/9 下午2:44
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {

	@Override
	public void handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HandlerMethod hm = (HandlerMethod) handler;
		Object controller = hm.getController();
		Method method = hm.getMethod();

		Object[] args = resolveParameters(request, method);
		// 执行处理器类的方法，也就是Controller类中的方法（通过反射执行）
		Object returnValue = method.invoke(controller, args);

		handleReturnValue(returnValue, response, method);
	}

	/**
	 * 结果处理
	 * 
	 * @param returnValue
	 * @param response
	 * @param method
	 */
	private void handleReturnValue(Object returnValue, HttpServletResponse response, Method method) throws IOException {
		if (method.isAnnotationPresent(ResponseBody.class)) {
			if (returnValue instanceof String) {
				response.setContentType("text/plain;charset=utf8");
				response.getWriter().write(returnValue.toString());
			} else {
				response.setContentType("application/json;charset=utf8");
				response.getWriter().write(JsonUtil.object2Json(returnValue));
			}
		} else {
			// 视图处理
		}
	}

	/**
	 * 参数处理
	 * 
	 * @param request
	 * @param method
	 * @return
	 */
	private Object[] resolveParameters(HttpServletRequest request, Method method) {
		List<Object> params = new ArrayList<Object>();
		Map<String, String[]> parameterMap = request.getParameterMap();
		// 获取方法的形参集合
		Parameter[] parameters = method.getParameters();
		for (Parameter parameter : parameters) {
			String[] stringValues = parameterMap.get(parameter.getName());
			if (stringValues == null || stringValues.length == 0) {
				continue;
			}
			// 获取到目标类型
			Class<?> type = parameter.getType();
			// 类型转换
			handleParameterType(stringValues, type, params);
		}
		return params.toArray();
	}

	/**
	 * 参数类型数据转换
	 * 
	 * @param stringValues
	 * @param type
	 * @param params
	 */
	private void handleParameterType(String[] stringValues, Class<?> type, List<Object> params) {
		if (type == Integer.class) {
			params.add(Integer.parseInt(stringValues[0]));
		} else if (type == String.class) {
			params.add(stringValues[0]);
		}
	}

	@Override
	public boolean support(Object handler) {
		return handler instanceof HandlerMethod;
	}
}
