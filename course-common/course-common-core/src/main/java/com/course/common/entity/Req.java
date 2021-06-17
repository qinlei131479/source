package com.course.common.entity;

import java.util.List;

import javax.validation.GroupSequence;

import com.baomidou.mybatisplus.annotation.TableField;
import com.course.common.utils.HuToolUtil;

import lombok.Data;

/**
 * 接口请求参数对象的父类
 *
 * @author qinlei
 * @date 2020/3/26 下午2:57
 */
@Data
public class Req<T> {
	@GroupSequence({ Create.class, Update.class })
	public interface GroupCreateUpdate {
	}

	public interface Create {
	}

	public interface Update {
	}

	public interface UpdateStatus {
	}

	public interface Delete {
	}

	/**
	 * 多个Id
	 */
	@TableField(exist = false)
	protected List<Long> idList;
	/**
	 * elastic-查询字段
	 */
	@TableField(exist = false)
	protected String queryField;

	/**
	 * 计算id
	 *
	 * @return
	 */
	public Object calId() {
		return HuToolUtil.getFieldValueIfExist(this, "id");
	}

}
