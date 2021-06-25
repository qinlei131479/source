package com.course.springboot.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.course.common.entity.Pg;
import com.course.common.entity.Req;
import com.course.common.entity.Res;
import com.course.springboot.entity.Dept;
import com.course.springboot.service.DeptService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 控制器：部门表
 *
 * @author qinlei
 * @date 2021/06/23 16:22
 */
@Slf4j
@io.swagger.annotations.Api(value = "dept", tags = "部门表模块")
@RestController
@RequiredArgsConstructor
public class DeptController {

	private final DeptService deptService;

	/**
	 * 分页查询
	 */
	@GetMapping("/dept/list")
	public Res list(Pg pg, Dept req) {
		pg.addOrderDefault(OrderItem.desc("t.id"));
		return Res.succ(deptService.findPg(pg, req));
	}

	/**
	 * 添加
	 */
	@PostMapping("/dept/create")
	public Res create(Pg pg, @RequestBody @Validated({ Req.Create.class }) Dept req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			return Res.succ((req.getId() == null) ? req : deptService.getById(req.getId()));
		}
		return deptService.create(req);
	}

	/**
	 * 修改
	 */
	@PostMapping("/dept/update")
	public Res update(Pg pg, @RequestBody @Validated({ Req.Update.class }) Dept req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			Dept resObj = this.deptService.getById(req.getId());
			return Res.succ(resObj);
		}
		return deptService.update(req);
	}

	/**
	 * 删除
	 */
	@PostMapping("/dept/delete")
	public Res delete(Pg pg, @RequestBody @Validated({ Req.Delete.class }) Dept req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			Dept resObj = this.deptService.getById(req.getId());
			return Res.succ(resObj);
		}
		return deptService.delete(req);
	}
}
