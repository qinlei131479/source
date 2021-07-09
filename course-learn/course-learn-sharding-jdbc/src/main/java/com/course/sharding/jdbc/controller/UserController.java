package com.course.sharding.jdbc.controller;

import java.util.Random;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.course.common.core.entity.Req;
import com.course.common.core.entity.Res;
import com.course.common.mybatis.entity.Pg;
import com.course.sharding.jdbc.entity.Department;
import com.course.sharding.jdbc.entity.Menu;
import com.course.sharding.jdbc.entity.User;
import com.course.sharding.jdbc.service.DepartmentService;
import com.course.sharding.jdbc.service.MenuService;
import com.course.sharding.jdbc.service.UserService;

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
public class UserController {

	private final UserService userService;
	private final DepartmentService departmentService;
	private final MenuService menuService;

	/**
	 * 分页查询
	 */
	@GetMapping("/list")
	public Res list(Pg pg, User req) {
		if (pg.checkListTypeOne()) {
			return Res.succ(userService.getDeptByUserId(req.getId()));
		}
		pg.addOrderDefault(OrderItem.desc("t.id"));
		return Res.succ(userService.findPg(pg, req));
	}

	/**
	 * 添加
	 */
	@PostMapping("/create")
	public Res create(Pg pg, @RequestBody @Validated({ Req.Create.class }) User req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			return Res.succ((req.getId() == null) ? req : userService.getById(req.getId()));
		}
		String name = "test";
		for (int m = 0; m < 20; m++) {
			String test = name + m;
			long deptId = IdWorker.getId();
			// 分库分表测试
			Department dept = new Department();
			dept.setId(IdWorker.getId());
			dept.setDeptId(deptId);
			dept.setName(test);
			departmentService.create(dept);

			User user = new User();
			user.setId(IdWorker.getId());
			user.setDeptId(deptId);
			user.setName(test);
			user.setPhone(String.valueOf(new Random().nextLong()));
			userService.create(user);

			Menu menu = new Menu();
			menu.setId(IdWorker.getId());
			menu.setName(test);
			menuService.create(menu);
		}

		return Res.succ();
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	public Res update(Pg pg, @RequestBody @Validated({ Req.Update.class }) User req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			User resObj = this.userService.getById(req.getId());
			return Res.succ(resObj);
		}
		return userService.update(req);
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Res delete(Pg pg, @RequestBody @Validated({ Req.Delete.class }) User req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			User resObj = this.userService.getById(req.getId());
			return Res.succ(resObj);
		}
		return userService.delete(req);
	}

}
