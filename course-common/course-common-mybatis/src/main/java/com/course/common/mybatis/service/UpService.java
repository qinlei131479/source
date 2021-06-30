package com.course.common.mybatis.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.course.common.core.entity.Req;
import com.course.common.core.entity.Res;
import com.course.common.mybatis.mapper.UpMapper;

/**
 * 统一方法入口
 * 
 * @author qinlei
 * @date 2021/6/23 下午12:20
 */
public interface UpService<T> extends IService<T> {

	/**
	 * 检查字段重复 -- 创建模式
	 * 
	 * @param req
	 * @param fieldAndNames
	 * @return
	 */
	Res<?> checkFieldRepeatCreate(T req, String... fieldAndNames);

	/**
	 * 检查字段重复 -- 创建模式
	 * 
	 * @param req
	 * @param eqNameList
	 * @param fieldAndNames
	 * @return
	 */
	Res<?> checkFieldRepeatCreate(T req, List<String> eqNameList, String... fieldAndNames);

	/**
	 * 检查字段重复 -- 修改模式
	 * 
	 * @param req
	 * @param fieldAndNames
	 * @return
	 */
	Res<?> checkFieldRepeatUpdate(T req, String... fieldAndNames);

	/**
	 * 检查字段重复 -- 修改模式
	 * 
	 * @param req
	 * @param eqNameList
	 * @param fieldAndNames
	 * @return
	 */
	Res<?> checkFieldRepeatUpdate(T req, List<String> eqNameList, String... fieldAndNames);

	/**
	 * 检查字段重复（包含eq相等字段）
	 * 
	 * @param req
	 * @param isCreate
	 * @param eqList
	 * @param fieldAndNames
	 *            字段名称对，使用冒号分割
	 * @return
	 */
	Res<?> checkFieldRepeat(T req, boolean isCreate, List<String> eqList, String... fieldAndNames);

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param req
	 * @return
	 */
	Page<T> findPg(Page page, T req);

	/**
	 * 列表查询，不支持排序和条数限制
	 * 
	 * @param req
	 * @return
	 */
	List<T> findList(T req);

	/**
	 * 列表查询，支持排序和条数限制，不查询总数
	 *
	 * @param page：排序和条数限制
	 * @param req：查询条件
	 * @return
	 */
	List<T> findList(Page page, T req);

	/**
	 * 修改
	 *
	 * @param req
	 * @return
	 */
	Res<?> update(T req);

	/**
	 * 修改
	 *
	 * @param req
	 * @return
	 */
	Res<?> update(UpMapper mapper, Req req);

	/**
	 * 添加
	 *
	 * @param req
	 * @return
	 */
	Res<?> create(T req);

	/**
	 * 添加，返回创建对象
	 *
	 * @param mapper
	 * @param req
	 * @return
	 */
	Res<?> create(UpMapper mapper, Req req);

	/**
	 * 添加，不返回创建对象
	 *
	 * @param mapper
	 * @param req
	 * @return
	 */
	void createVoid(UpMapper mapper, Req req);

	/**
	 * 删除
	 *
	 * @param req
	 * @return
	 */
	Res<?> delete(T req);

	/**
	 * 删除
	 *
	 * @param id
	 * @return
	 */
	Res<?> deleteById(Long id);
}
