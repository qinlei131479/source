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
import com.course.springboot.entity.Config;
import com.course.springboot.service.ConfigService;

/**
 * 控制器：参数配置表
 *
 * @author qinlei
 * @date   2021/06/23 16:22
 */
@io.swagger.annotations.Api(value = "config", tags = "参数配置表模块")
@RestController
@RequiredArgsConstructor
public class ConfigController {

	private final  ConfigService configService;

	/**
	 * 分页查询
	 */
	@GetMapping("/config/list")
	public Res list(Pg pg, Config req) {
		pg.addOrderDefault(OrderItem.desc("t.id"));
		return Res.succ(configService.findPg(pg,req));
	}

	/**
	 * 添加
	 */
	@PostMapping("/config/create")
	public Res create(Pg pg, @RequestBody @Validated({ Req.Create.class }) Config req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			return Res.succ((req.getId() == null) ? req : configService.getById(req.getId()));
		}
		return configService.create(req);
	}

	/**
	 * 修改
	 */
	@PostMapping("/config/update")
	public Res update(Pg pg, @RequestBody @Validated({ Req.Update.class }) Config req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			Config resObj = this.configService.getById(req.getId());
			return Res.succ(resObj);
		}
		return configService.update(req);
	}

	/**
	 * 删除
	 */
	@PostMapping("/config/delete")
	public Res delete(Pg pg, @RequestBody @Validated({ Req.Delete.class }) Config req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			Config resObj = this.configService.getById(req.getId());
			return Res.succ(resObj);
		}
		return configService.delete(req);
	}
}
