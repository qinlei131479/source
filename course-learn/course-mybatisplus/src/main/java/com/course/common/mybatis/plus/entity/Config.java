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
 * 参数配置表
 * </p>
 *
 * @author qinlei
 * @since 2021-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Config implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 参数主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数键名
     */
    private String key;

    /**
     * 参数键值
     */
    private String value;

    /**
     * 系统内置（1是 0否）
     */
    @TableField("isInner")
    private Integer isInner;

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
