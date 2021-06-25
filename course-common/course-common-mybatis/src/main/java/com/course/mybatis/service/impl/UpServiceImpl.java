package com.course.mybatis.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.course.common.config.BaseGlobalService;
import com.course.common.entity.Req;
import com.course.common.entity.Res;
import com.course.common.utils.HuToolUtil;
import com.course.common.utils.SpringContextHolder;
import com.course.mybatis.mapper.UpMapper;
import com.course.mybatis.service.UpService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 统一类实现类
 * 
 * @author qinlei
 * @date 2021/6/23 下午12:21
 */
public class UpServiceImpl<M extends UpMapper<T>, T extends Req> extends ServiceImpl<M, T> implements UpService<T> {
	/**
	 * 检查属性重复 -- 创建模式
	 *
	 * @param req
	 * @param fieldAndNames
	 *            字段名称对，使用冒号分割
	 * @return
	 */
	@Override
	public Res<?> checkFieldRepeatCreate(T req, String... fieldAndNames) {
		return checkFieldRepeat(req, true, null, fieldAndNames);
	}

	/**
	 * 检查属性重复 -- 创建模式
	 *
	 * @param req
	 * @param fieldAndNames
	 *            字段名称对，使用冒号分割
	 * @return
	 */
	@Override
	public Res<?> checkFieldRepeatCreate(T req, List<String> eqNameList, String... fieldAndNames) {
		return checkFieldRepeat(req, true, eqNameList, fieldAndNames);
	}

	/**
	 * 检查属性重复 -- 修改模式
	 *
	 * @param req
	 * @param fieldAndNames
	 *            字段名称对，使用冒号分割
	 * @return
	 */
	@Override
	public Res<?> checkFieldRepeatUpdate(T req, String... fieldAndNames) {
		return checkFieldRepeat(req, false, null, fieldAndNames);
	}

	/**
	 * 检查属性重复 -- 修改模式
	 *
	 * @param req
	 * @param fieldAndNames
	 *            字段名称对，使用冒号分割
	 * @return
	 */
	@Override
	public Res<?> checkFieldRepeatUpdate(T req, List<String> eqNameList, String... fieldAndNames) {
		return checkFieldRepeat(req, false, eqNameList, fieldAndNames);
	}

	/**
	 * 检查字段重复（包含eq相等字段）
	 *
	 * @param req
	 * @param eqNameList
	 *            使用冒号分割
	 * @param fieldAndNames
	 *            字段名称对，使用冒号分割
	 * @return
	 */
	@Override
	public Res<?> checkFieldRepeat(T req, boolean isCreate, List<String> eqNameList, String... fieldAndNames) {
		for (String fieldAndName : fieldAndNames) {
			String[] fn = fieldAndName.split(":");
			String field = fn[0];
			String name = fn[1];
			QueryWrapper<T> queryWrapper = new QueryWrapper<>();
			Object object = HuToolUtil.getFieldValueIfExist(req, field);
			if (object != null && StrUtil.isNotBlank(object.toString())) {
				queryWrapper.eq(field, object);
				if (CollUtil.isNotEmpty(eqNameList)) {
					eqNameList.forEach(item -> {
						queryWrapper.eq(item, HuToolUtil.getFieldValueIfExist(req, item));
					});
				}
				Object id = req.calId();
				// 非创建情况下，增加id条件
				if (id != null && isCreate == false) {
					queryWrapper.ne("id", id);
				}
				int repeatCount = this.baseMapper.selectCount(queryWrapper);
				if (repeatCount > 0) {
					throw Res.fail(name + "重复", field).toBaseRuntimeException();
				}
			}
		}
		return null;
	}

	@Override
	public Page<T> findPg(Page page, T req) {
		return baseMapper.findPg(page, req);
	}

	@Override
	public List<T> findList(T req) {
		return baseMapper.findList(req);
	}

	@Override
	public List<T> findList(Page page, T req) {
		if (page != null) {
			// 数量不限制
			page.setSize(Integer.MAX_VALUE);
			// 不查询总数
			page.setSearchCount(false);
		}
		return baseMapper.findList(page, req);
	}

	@Override
	public Res<?> update(T req) {
		baseMapper.updateCustom(req);
		return Res.succ();
	}

	@Override
	public Res<?> update(UpMapper mapper, Req req) {
		mapper.updateCustom(req);
		return Res.succ();
	}

	@Override
	public Res<?> create(T req) {
		return this.create(this.baseMapper, req);
	}

	@Override
	public Res<?> create(UpMapper mapper, Req req) {
		this.createVoid(mapper, req);
		return Res.succ();
	}

	@Override
	public void createVoid(UpMapper mapper, Req req) {
		// bean不能用注入的方式，否则依赖关系有问题
		BaseGlobalService globalService = SpringContextHolder.getBean(BaseGlobalService.class);
		HuToolUtil.setFieldValueIfExist(req, "id", globalService.nextId());
		HuToolUtil.setFieldValueIfExist(req, "createTime", null);
		HuToolUtil.setFieldValueIfExist(req, "updateTime", null);
		mapper.insert(req);
	}

	@Override
	public Res<?> delete(T req) {
		return this.deleteById((Long) req.calId());
	}

	@Override
	public Res<?> deleteById(Long id) {
		baseMapper.deleteById(id);
		return Res.succ();
	}
}
