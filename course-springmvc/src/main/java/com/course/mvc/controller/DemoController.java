package com.course.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import com.course.mvc.annotations.Controller;
import com.course.mvc.annotations.RequestMapping;
import com.course.mvc.annotations.ResponseBody;

/**
 * @author qinlei
 * @date 2021/6/9 下午2:33
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> query(Integer id, String name) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		paramMap.put("name", "wangwu" + name);
		return paramMap;
	}

	@RequestMapping("/add")
	@ResponseBody
	public String add() {
		return "paramMap add success";
	}
}
