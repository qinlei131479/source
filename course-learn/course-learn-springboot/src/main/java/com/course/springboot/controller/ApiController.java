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
import com.course.common.mybatis.entity.Pg;
import com.course.springboot.entity.Api;
import com.course.springboot.service.ApiService;

/**
 * 控制器：应用接口表
 *
 * @author qinlei
 * @date   2021/06/28 12:13
 */
@io.swagger.annotations.Api(value = "api", tags = "应用接口表模块")
@RestController
@RequiredArgsConstructor
public class ApiController {

	private final  ApiService apiService;

	/**
	 * 分页查询
	 */
	@GetMapping("/api/list")
	public Res list(Pg pg, Api req) {
		pg.addOrderDefault(OrderItem.desc("t.id"));
		return Res.succ(apiService.findPg(pg,req));
	}

	/**
	 * 添加
	 */
	@PostMapping("/api/create")
	public Res create(Pg pg, @RequestBody @Validated({ Req.Create.class }) Api req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			return Res.succ((req.getId() == null) ? req : apiService.getById(req.getId()));
		}
		return apiService.create(req);
	}

	/**
	 * 修改
	 */
	@PostMapping("/api/update")
	public Res update(Pg pg, @RequestBody @Validated({ Req.Update.class }) Api req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			Api resObj = this.apiService.getById(req.getId());
			return Res.succ(resObj);
		}
		return apiService.update(req);
	}

	/**
	 * 删除
	 */
	@PostMapping("/api/delete")
	public Res delete(Pg pg, @RequestBody @Validated({ Req.Delete.class }) Api req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			Api resObj = this.apiService.getById(req.getId());
			return Res.succ(resObj);
		}
		return apiService.delete(req);
	}
}
