package com.course.mybatis.plus.entity;

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
 * 操作日志记录
 * </p>
 *
 * @author qinlei
 * @since 2021-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_log")
public class Log implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 日志主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模块标题
     */
    private String title;

    /**
     * 应用
     */
    @TableField("appName")
    private String appName;

    /**
     * token
     */
    private String token;

    /**
     * 用户id
     */
    @TableField("userId")
    private Long userId;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 操作地点
     */
    private String location;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 主机地址
     */
    private String ip;

    /**
     * 执行时间
     */
    @TableField("handleTime")
    private Integer handleTime;

    /**
     * 请求方式
     */
    @TableField("requestMethod")
    private String requestMethod;

    /**
     * 请求参数
     */
    @TableField("requestParam")
    private String requestParam;

    /**
     * 返回参数
     */
    private String result;

    /**
     * 错误消息
     */
    @TableField("errorMsg")
    private String errorMsg;

    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;

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
