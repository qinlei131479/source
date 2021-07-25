package com.course.biz.sys.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.course.common.core.entity.Req;

/**
 * 实体类：菜单接口表
 *
 * @author qinlei
 * @date   2021/06/28 15:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuApi extends Req<MenuApi> {
    @TableId(type = IdType.INPUT)
	private Long id;
    
	private Long menuId;
    
	private String apiPath;
}
