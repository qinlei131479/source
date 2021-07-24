package com.course.mybatis.tests;

import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.course.mybatis.plus.entity.Config;
import com.course.mybatis.plus.mapper.ConfigMapper;
import com.course.mybatis.plus.mapper.UserMapper;

/**
 * @author qinlei
 * @date 2021/7/24 下午5:59
 */
@SpringBootTest
public class SpringBootTests {

	@Resource
	private UserMapper userMapper;

	@Resource
	private ConfigMapper configMapper;

	@Test
	public void testSelect() {
		QueryWrapper<Config> queryWrapper = new QueryWrapper<>();
		String isInner = "";
		queryWrapper.select("id,name,value,isInner,createBy,createTime,updateBy,updateTime,remark")
				.eq(StringUtils.isNotBlank(isInner), "isInner", isInner);

		List<Config> userList = configMapper.selectList(queryWrapper);
		userList.forEach(user -> System.out.println(user.getName()));
	}
}
