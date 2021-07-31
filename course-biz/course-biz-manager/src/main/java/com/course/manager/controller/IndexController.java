package com.course.manager.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.common.core.entity.Res;

/**
 * @author qinlei
 * @date 2021/7/31 下午2:12
 */
@RestController
public class IndexController {

	@PostMapping("/user/getUser")
	public Object index() {
		return Res.succ(SecurityContextHolder.getContext().getAuthentication());
	}

}
