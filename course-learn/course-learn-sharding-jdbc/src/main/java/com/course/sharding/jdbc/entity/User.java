package com.course.sharding.jdbc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author qinlei
 * @date 2021/7/6 下午6:01
 */
@Data
@Builder
@TableName("user")
@EqualsAndHashCode(callSuper = false)
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

 private Long id;
 private Long corpId;
 private String createTime;
 private Integer isDelete;
 private String updateTime;
 private String avatar;
 private String deptArray;
 private Long deptId;
 private String flag;
 private String isAdmin;
 private String name;
 private String openId;
 private String password;
 private String phone;
 private String post;
 private String username;
 private String workUnit;
 private String isManager;
 private Integer isDeptHead;


}
