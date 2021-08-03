package com.course.common.core.entity;

import com.course.common.core.utils.HuToolUtil;

import lombok.Data;

/**
 * 接口请求参数对象的父类
 *
 * @author qinlei
 * @date 2020/3/26 下午2:57
 */
@Data
public class Req<T> {

	public interface Create {
	}

	public interface Update {
	}

	public interface UpdateStatus {
	}

	public interface Delete {
	}

	/**
	 * 计算id
	 *
	 * @return
	 */
	public Object calId() {
		return HuToolUtil.getFieldValueIfExist(this, "id");
	}

}
