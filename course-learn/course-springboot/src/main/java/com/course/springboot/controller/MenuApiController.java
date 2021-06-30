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
import com.course.springboot.entity.MenuApi;
import com.course.springboot.service.MenuApiService;

/**
 * 控制器：菜单接口表
 *
 * @author qinlei
 * @date   2021/06/28 15:35
 */
@io.swagger.annotations.Api(value = "menuApi", tags = "菜单接口表模块")
@RestController
@RequiredArgsConstructor
public class MenuApiController {

	private final  MenuApiService menuApiService;

	/**
	 * 分页查询
	 */
	@GetMapping("/menuApi/list")
	public Res list(Pg pg, MenuApi req) {
		pg.addOrderDefault(OrderItem.desc("t.id"));
		return Res.succ(menuApiService.findPg(pg,req));
	}

	/**
	 * 添加
	 */
	@PostMapping("/menuApi/create")
	public Res create(Pg pg, @RequestBody @Validated({ Req.Create.class }) MenuApi req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			return Res.succ((req.getId() == null) ? req : menuApiService.getById(req.getId()));
		}
		return menuApiService.create(req);
	}

	/**
	 * 修改
	 */
	@PostMapping("/menuApi/update")
	public Res update(Pg pg, @RequestBody @Validated({ Req.Update.class }) MenuApi req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			MenuApi resObj = this.menuApiService.getById(req.getId());
			return Res.succ(resObj);
		}
		return menuApiService.update(req);
	}

	/**
	 * 删除
	 */
	@PostMapping("/menuApi/delete")
	public Res delete(Pg pg, @RequestBody @Validated({ Req.Delete.class }) MenuApi req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			MenuApi resObj = this.menuApiService.getById(req.getId());
			return Res.succ(resObj);
		}
		return menuApiService.delete(req);
	}
}
