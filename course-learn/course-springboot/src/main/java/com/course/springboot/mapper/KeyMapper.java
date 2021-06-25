package com.course.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.course.mybatis.mapper.UpMapper;
import com.course.springboot.entity.Key;

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
