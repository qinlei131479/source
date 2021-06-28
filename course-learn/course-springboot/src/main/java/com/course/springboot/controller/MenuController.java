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
import com.course.springboot.entity.Menu;
import com.course.springboot.service.MenuService;

/**
 * 控制器：菜单表
 *
 * @author qinlei
 * @date   2021/06/28 15:35
 */
@io.swagger.annotations.Api(value = "menu", tags = "菜单表模块")
@RestController
@RequiredArgsConstructor
public class MenuController {

	private final  MenuService menuService;

	/**
	 * 分页查询
	 */
	@GetMapping("/menu/list")
	public Res list(Pg pg, Menu req) {
		pg.addOrderDefault(OrderItem.desc("t.id"));
		return Res.succ(menuService.findPg(pg,req));
	}

	/**
	 * 添加
	 */
	@PostMapping("/menu/create")
	public Res create(Pg pg, @RequestBody @Validated({ Req.Create.class }) Menu req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			return Res.succ((req.getId() == null) ? req : menuService.getById(req.getId()));
		}
		return menuService.create(req);
	}

	/**
	 * 修改
	 */
	@PostMapping("/menu/update")
	public Res update(Pg pg, @RequestBody @Validated({ Req.Update.class }) Menu req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			Menu resObj = this.menuService.getById(req.getId());
			return Res.succ(resObj);
		}
		return menuService.update(req);
	}

	/**
	 * 删除
	 */
	@PostMapping("/menu/delete")
	public Res delete(Pg pg, @RequestBody @Validated({ Req.Delete.class }) Menu req, BindingResult result) {
		if (pg.checkActionStatusInit()) {
			Menu resObj = this.menuService.getById(req.getId());
			return Res.succ(resObj);
		}
		return menuService.delete(req);
	}
}
