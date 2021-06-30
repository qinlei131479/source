package com.course.springboot.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.OrderItem;

import lombok.RequiredArgsConstructor;
import com.course.common.core.entity.Req;
import com.course.common.core.entity.Res;
import com.course.common.core.entity.Pg;
import com.course.springboot.entity.User;
import com.course.springboot.service.UserService;

/**
 * 控制器：用户信息表
 *
 * @author qinlei
 * @date   2021/06/23 16:22
 */
@io.swagger.annotations.Api(value = "user", tags = "用户信息表模块")
@RestController
@RequiredArgsConstructor
public class UserController {

	private final  UserService userService;

	/**
	 * 分页查询
	 */
	@GetMapping("/user/list")
	public Res list(Pg pg, User req) {
		pg.addOrderDefault(OrderItem.desc("t.id"));
		return Res.succ(userService.findPg(pg,req));
	}

	/**
	 * 添加
	 */
	@PostMapping("/user/create")
	public Res create(Pg pg, @RequestBody @Validated({ Req.Create.class }) User req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			return Res.succ((req.getId() == null) ? req : userService.getById(req.getId()));
		}
		return userService.create(req);
	}

	/**
	 * 修改
	 */
	@PostMapping("/user/update")
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
	@PostMapping("/user/delete")
	public Res delete(Pg pg, @RequestBody @Validated({ Req.Delete.class }) User req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			User resObj = this.userService.getById(req.getId());
			return Res.succ(resObj);
		}
		return userService.delete(req);
	}
}
