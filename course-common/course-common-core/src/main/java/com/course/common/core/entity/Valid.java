package com.course.common.core.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 验证参数字段
 * 
 * @author qinlei
 * @date 2021/6/24 下午5:26
 */
@Data
@AllArgsConstructor
public class Valid {

	private String field;
	private List<ValidItem> validItemList;
}
