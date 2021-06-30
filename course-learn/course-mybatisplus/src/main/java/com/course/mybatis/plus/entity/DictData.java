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
 * 字典数据表
 * </p>
 *
 * @author qinlei
 * @since 2021-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dict_data")
public class DictData implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 字典编码
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典排序
     */
    private Integer weight;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典键值
     */
    private String value;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 样式属性（其他样式扩展）
     */
    @TableField("cssClass")
    private String cssClass;

    /**
     * 表格回显样式
     */
    @TableField("listClass")
    private String listClass;

    /**
     * 是否默认（1是 0否）
     */
    @TableField("isDefault")
    private Integer isDefault;

    /**
     * 状态（0正常 1停用）
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
