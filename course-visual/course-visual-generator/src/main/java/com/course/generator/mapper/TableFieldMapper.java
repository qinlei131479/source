package com.course.generator.mapper;

import java.util.List;

import com.course.common.mybatis.mapper.UpMapper;
import com.course.generator.entity.TableField;
import org.apache.ibatis.annotations.Mapper;


/**
 * Mapper接口
 *
 * @author qinlei
 * @date 2020/3/26 下午2:57
 */
@Mapper
public interface TableFieldMapper extends UpMapper<TableField> {
	/**
	 * 查表的字段列表
	 *
	 * @param req
	 * @return
	 */
	List<TableField> findTableFieldListByTableName(TableField req);

	/**
	 * 查表的注释
	 *
	 * @param req
	 * @return
	 */
	String findTableComment(TableField req);
}
