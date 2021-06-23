package com.course.common.entity;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course.common.enums.ActionStatusEnum;
import com.course.common.enums.ListTypeEnum;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页参数对象的扩展
 *
 * @author qinlei
 * @date 2020/3/26 下午2:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Pg<T> extends Page<T> {
	private static final long serialVersionUID = 7148080350889774725L;

	/**
	 * 查询类型，见枚举 ListTypeEnum
	 */
	protected String listType;

	public boolean checkListTypeOne() {
		return (ListTypeEnum.one.getCode().equals(this.listType));
	}

	public boolean checkListTypeArray() {
		return (ListTypeEnum.array.getCode().equals(this.listType));
	}

	public boolean checkListTypePage() {
		return (ListTypeEnum.page.getCode().equals(this.listType));
	}

	public boolean checkListType(String listType) {
		return (listType != null && listType.equals(this.listType));
	}

	/**
	 * 操作状态，见枚举:ActionStatusEnum
	 */
	protected String actionStatus;

	/**
	 * 判断操作状态，action状态
	 *
	 * @param actionStatusEnum
	 * @return
	 */
	public boolean checkActionStatus(ActionStatusEnum actionStatusEnum) {
		return (actionStatusEnum.getCode().equals(this.actionStatus));
	}

	public boolean checkActionStatusInit() {
		return checkActionStatus(ActionStatusEnum.init);
	}

	public boolean checkActionStatusCheck() {
		return checkActionStatus(ActionStatusEnum.check);
	}

	/**
	 * 当排序为空的时候，增加默认排序
	 * 
	 * @param item
	 */
	public void addOrderDefault(OrderItem... item) {
		if (CollUtil.isEmpty(this.getOrders())) {
			this.addOrder(item);
		}
	}
}
