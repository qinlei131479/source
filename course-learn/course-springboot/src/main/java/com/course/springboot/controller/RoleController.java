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
import com.course.springboot.entity.Role;
import com.course.springboot.service.RoleService;

/**
 * 控制器：角色信息表
 *
 * @author qinlei
 * @date   2021/06/30 17:55
 */
@io.swagger.annotations.Api(value = "role", tags = "角色信息表模块")
@RestController
@RequiredArgsConstructor
public class RoleController {

	private final  RoleService roleService;

	/**
	 * 分页查询
	 */
	@GetMapping("/role/list")
	public Res list(Pg pg, Role req) {
		pg.addOrderDefault(OrderItem.desc("t.id"));
		return Res.succ(roleService.findPg(pg,req));
	}

	/**
	 * 添加
	 */
	@PostMapping("/role/create")
	public Res create(Pg pg, @RequestBody @Validated({ Req.Create.class }) Role req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			return Res.succ((req.getId() == null) ? req : roleService.getById(req.getId()));
		}
		return roleService.create(req);
	}

	/**
	 * 修改
	 */
	@PostMapping("/role/update")
	public Res update(Pg pg, @RequestBody @Validated({ Req.Update.class }) Role req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			Role resObj = this.roleService.getById(req.getId());
			return Res.succ(resObj);
		}
		return roleService.update(req);
	}

	/**
	 * 删除
	 */
	@PostMapping("/role/delete")
	public Res delete(Pg pg, @RequestBody @Validated({ Req.Delete.class }) Role req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			Role resObj = this.roleService.getById(req.getId());
			return Res.succ(resObj);
		}
		return roleService.delete(req);
	}
}
