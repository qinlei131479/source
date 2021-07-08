package com.course.sharding.jdbc.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.course.common.core.entity.Req;

/**
 * 实体类：
 *
 * @author qinlei
 * @date   2021/07/08 21:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Config extends Req<Config> {

    @TableId(type = IdType.INPUT)
	private Long id;
    
	private String remark;
    
	private Date create_time;
    
	private Date last_modify_time;
}
