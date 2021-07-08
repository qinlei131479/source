package com.course.sharding.jdbc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.course.common.core.entity.Req;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统菜单
 * </p>
 *
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Menu extends Req<Menu> {

	private Long id;
	@TableField("corp_id")
	private String corpId;
	@TableField("create_time")
	private String createTime;
	@TableField("all_path")
	private String allPath;
	@TableField("is_delete")
	private Integer isDelete;
	@TableField("update_time")
	private String updateTime;
	private String code;
	private String icon;
	private String name;
	private Integer num;
	@TableField("parent_id")
	private String parentId;
	private String path;
	private String type;
	private String url;

}
