package com.course.sharding.jdbc.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.course.common.core.entity.Res;
import com.course.common.mybatis.service.impl.UpServiceImpl;
import com.course.sharding.jdbc.entity.Department;
import com.course.sharding.jdbc.entity.Menu;
import com.course.sharding.jdbc.entity.User;
import com.course.sharding.jdbc.mapper.DepartmentMapper;
import com.course.sharding.jdbc.mapper.MenuMapper;
import com.course.sharding.jdbc.mapper.UserMapper;
import com.course.sharding.jdbc.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends UpServiceImpl<UserMapper, User> implements UserService {

	private final UserMapper userMapper;
	private final MenuMapper menuMapper;
	private final DepartmentMapper departmentMapper;

	@Override
	public Res<?> create(User req) {
		for (int m = 0; m < 20; m++) {
			long deptId = IdWorker.getId();
			// 分库分表测试
			Department dept = new Department();
			dept.setId(IdWorker.getId());
			dept.setDeptId(deptId);
			dept.setName("测试" + String.valueOf(m));
			createVoid(departmentMapper, dept);

			User user = new User();
			user.setId(IdWorker.getId());
			user.setDeptId(deptId);
			user.setName("测试" + String.valueOf(m));
			user.setPhone(String.valueOf(new Random().nextLong()));
			super.create(user);

			Menu menu = new Menu();
			menu.setId(IdWorker.getId());
			menu.setName("测试" + String.valueOf(m));
			createVoid(menuMapper, menu);
		}
		return Res.succ();
	}

	@Override
	public List<Department> getDeptByUserId(Long userId) {
		return userMapper.getDeptByUserId(userId);
	}
}
