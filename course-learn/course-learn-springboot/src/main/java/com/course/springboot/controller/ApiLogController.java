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
import com.course.springboot.entity.ApiLog;
import com.course.springboot.service.ApiLogService;

/**
 * 控制器：接口日志表
 *
 * @author qinlei
 * @date   2021/06/28 12:13
 */
@io.swagger.annotations.Api(value = "apiLog", tags = "接口日志表模块")
@RestController
@RequiredArgsConstructor
public class ApiLogController {

	private final  ApiLogService apiLogService;

	/**
	 * 分页查询
	 */
	@GetMapping("/apiLog/list")
	public Res list(Pg pg, ApiLog req) {
		pg.addOrderDefault(OrderItem.desc("t.id"));
		return Res.succ(apiLogService.findPg(pg,req));
	}

	/**
	 * 添加
	 */
	@PostMapping("/apiLog/create")
	public Res create(Pg pg, @RequestBody @Validated({ Req.Create.class }) ApiLog req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			return Res.succ((req.getId() == null) ? req : apiLogService.getById(req.getId()));
		}
		return apiLogService.create(req);
	}

	/**
	 * 修改
	 */
	@PostMapping("/apiLog/update")
	public Res update(Pg pg, @RequestBody @Validated({ Req.Update.class }) ApiLog req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			ApiLog resObj = this.apiLogService.getById(req.getId());
			return Res.succ(resObj);
		}
		return apiLogService.update(req);
	}

	/**
	 * 删除
	 */
	@PostMapping("/apiLog/delete")
	public Res delete(Pg pg, @RequestBody @Validated({ Req.Delete.class }) ApiLog req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			ApiLog resObj = this.apiLogService.getById(req.getId());
			return Res.succ(resObj);
		}
		return apiLogService.delete(req);
	}
}
