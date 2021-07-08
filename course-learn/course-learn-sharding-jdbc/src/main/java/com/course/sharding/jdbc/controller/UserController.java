package com.course.sharding.jdbc.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.course.common.mybatis.entity.Pg;
import com.course.common.core.entity.Req;
import com.course.common.core.entity.Res;
import com.course.sharding.jdbc.entity.User;
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
		return userService.create(req);
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
