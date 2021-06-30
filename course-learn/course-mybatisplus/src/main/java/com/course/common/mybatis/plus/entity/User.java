package com.course.common.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author qinlei
 * @since 2021-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class User implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 部门ID
     */
    @TableField("deptId")
    private Long deptId;

    /**
     * 用户账号
     */
    private String name;

    /**
     * 用户昵称
     */
    @TableField("nickName")
    private String nickName;

    /**
     * 用户类型（00系统用户）
     */
    private String type;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private Integer sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableField("isDelete")
    private Integer isDelete;

    /**
     * 最后登陆IP
     */
    @TableField("loginIp")
    private String loginIp;

    /**
     * 最后登陆时间
     */
    @TableField("loginTime")
    private LocalDateTime loginTime;

    /**
     * 创建者
     */
    @TableField("createBy")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField("updateBy")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;


}
