package com.course.common.mybatis.config;

import java.util.Random;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 属性字段自动填充 在需要的字段上加上@TableField(fill = FieldFill.INSERT_UPDATE)即可
 * 
 * @author qinlei
 * @date 2021/7/9 上午11:32
 */
@Slf4j
@Component
public class DataAutoFill implements MetaObjectHandler {

	private final String CREATE_USER_ID = "createUserId";
	private final String UPDATE_USER_ID = "updateUserId";

	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("DataAutoFill ");
		this.strictInsertFill(metaObject, CREATE_USER_ID, Long.class, 1L);
		this.strictInsertFill(metaObject, UPDATE_USER_ID, Long.class, 1L);
		this.strictInsertFill(metaObject, "password", String.class, IdWorker.get32UUID());
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.strictUpdateFill(metaObject, UPDATE_USER_ID, Long.class, 2L);
	}
}
