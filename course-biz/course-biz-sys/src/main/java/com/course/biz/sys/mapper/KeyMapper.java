package com.course.biz.sys.mapper;

import com.course.biz.sys.entity.Key;
import org.apache.ibatis.annotations.Mapper;

import com.course.common.mybatis.mapper.UpMapper;

/**
 * Mapper接口：全局唯一ID
 * 
 * @author qinlei
 * @date 2021/6/25 下午5:51
 */
@Mapper
public interface KeyMapper extends UpMapper<Key> {
	/**
	 * 更新key
	 * 
	 * @param key
	 */
	void updateKey(Key key);
}
