package com.course.springboot.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.OrderItem;

import lombok.RequiredArgsConstructor;
import com.course.common.entity.Req;
import com.course.common.entity.Res;
import com.course.common.entity.Pg;
import com.course.springboot.entity.Attach;
import com.course.springboot.service.AttachService;

/**
 * 控制器：附件表
 *
 * @author qinlei
 * @date   2021/06/28 12:13
 */
@io.swagger.annotations.Api(value = "attach", tags = "附件表模块")
@RestController
@RequiredArgsConstructor
public class AttachController {

	private final  AttachService attachService;

	/**
	 * 分页查询
	 */
	@GetMapping("/attach/list")
	public Res list(Pg pg, Attach req) {
		pg.addOrderDefault(OrderItem.desc("t.id"));
		return Res.succ(attachService.findPg(pg,req));
	}

	/**
	 * 添加
	 */
	@PostMapping("/attach/create")
	public Res create(Pg pg, @RequestBody @Validated({ Req.Create.class }) Attach req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			return Res.succ((req.getId() == null) ? req : attachService.getById(req.getId()));
		}
		return attachService.create(req);
	}

	/**
	 * 修改
	 */
	@PostMapping("/attach/update")
	public Res update(Pg pg, @RequestBody @Validated({ Req.Update.class }) Attach req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			Attach resObj = this.attachService.getById(req.getId());
			return Res.succ(resObj);
		}
		return attachService.update(req);
	}

	/**
	 * 删除
	 */
	@PostMapping("/attach/delete")
	public Res delete(Pg pg, @RequestBody @Validated({ Req.Delete.class }) Attach req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			Attach resObj = this.attachService.getById(req.getId());
			return Res.succ(resObj);
		}
		return attachService.delete(req);
	}
}
