package com.course.sharding.jdbc.controller;

import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.course.sharding.jdbc.commons.ResponseBuilder;
import com.course.sharding.jdbc.entity.Department;
import com.course.sharding.jdbc.entity.Menu;
import com.course.sharding.jdbc.entity.User;
import com.course.sharding.jdbc.service.DepartmentService;
import com.course.sharding.jdbc.service.MenuService;
import com.course.sharding.jdbc.service.UserService;
import com.google.common.collect.ImmutableMap;

import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;

/**
 * User
 *
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends ResponseBuilder {

	private final UserService userService;
	private final DepartmentService departmentService;
	private final MenuService menuService;

	@GetMapping("/add")
	public Object add() {
		for (int m = 0; m < 120; m++) {
			long deptId = IdWorker.getId();
			// 分库分表测试
			Department dept = Department.builder().deptId(deptId).createTime(buildCreateTime()).id(IdWorker.getId())
					.name("测试" + String.valueOf(m)).build();

			User build = User.builder().id(IdWorker.getId()).isDelete(1).name("测试" + String.valueOf(m))
					.phone(String.valueOf(new Random().nextLong())).deptId(deptId).createTime(buildCreateTime())
					.build();

			Menu menu = Menu.builder().id(IdWorker.getId()).createTime(buildCreateTime()).isDelete(1)
					.name("测试" + String.valueOf(m)).build();
			departmentService.save(dept);
			userService.save(build);
			menuService.save(menu);
		}
		return success();
	}

	private String buildCreateTime() {
		return DateUtil.now();
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("/list")
	public Object list() {
		QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
		departmentQueryWrapper.likeRight("name", "测试5").eq("id", "");
		List<Department> departmentList = departmentService.listByParam("测试5");
		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		// 非空判断写法
		lambdaQueryWrapper.likeRight(true, User::getName, "测试2").eq(User::getIsDelete, 1);
		List<User> userList = userService.list(lambdaQueryWrapper);
		List<Menu> menuList = menuService.list(new QueryWrapper<Menu>().lambda().eq(Menu::getName, "测试100"));
		return success(ImmutableMap.of("departmentList", departmentList, "userList", userList, "menuList", menuList));
	}

	/**
	 * 多表联查
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/listById")
	public Object getDeptByUserId(@RequestParam("userId") String userId) {

		return success(userService.getDeptByUserId(userId));
	}

}
