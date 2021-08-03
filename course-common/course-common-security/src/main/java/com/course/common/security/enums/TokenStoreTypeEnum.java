package com.course.common.security.enums;

import lombok.Getter;

/**
 * TokenStore存储方式
 * 
 * @author qinlei
 * @date 2021/8/2 下午10:20
 */
@Getter
public enum TokenStoreTypeEnum {
	jwt, redis
}
