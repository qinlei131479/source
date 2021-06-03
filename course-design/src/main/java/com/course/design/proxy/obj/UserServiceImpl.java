package com.course.design.proxy.obj;

import com.course.design.proxy.obj.UserService;

/**
 * @author qinlei
 * @date 2021/6/3 下午3:24
 */
public class UserServiceImpl implements UserService {

    @Override
    public void saveUser() {
        System.out.println("添加用户");
    }
}
