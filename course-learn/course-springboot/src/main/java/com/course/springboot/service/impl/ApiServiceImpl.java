package com.course.springboot.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.course.common.entity.Req;
import com.course.common.entity.Res;
import com.course.common.enums.FlagEnum;
import com.course.common.enums.RedisKeyEnum;
import com.course.common.utils.HuToolUtil;
import com.course.common.mybatis.service.impl.UpServiceImpl;
import com.course.springboot.entity.Api;
import com.course.springboot.enums.ApiDefaultNameEnum;
import com.course.springboot.mapper.ApiMapper;
import com.course.springboot.mapper.MenuApiMapper;
import com.course.springboot.mapper.MenuMapper;
import com.course.springboot.service.ApiService;
import com.course.springboot.service.ConfigService;

import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;

/**
 * service实现类：应用接口表
 *
 * @author qinlei
 * @date 2021/06/28 12:13
 */
@Service
@RequiredArgsConstructor
public class ApiServiceImpl extends UpServiceImpl<ApiMapper, Api> implements ApiService {

	private final ConfigService configService;
	private final MenuMapper menuMapper;
	private final MenuApiMapper menuApiMapper;

	@Override
	public Res<?> create(Api req) {
		List<String> list = HuToolUtil.convertStringSplitByDouToList(req.getActions());
		if (CollUtil.isNotEmpty(list)) {
			for (String action : list) {
				String path = HuToolUtil.joinDir(req.getPath(), action);
				Api api = baseMapper.selectOne(
						Wrappers.<Api> query().lambda().eq(Api::getPath, path).eq(Api::getPlatform, req.getPlatform()));
				if (api == null) {
					req.setNeedLogFlag(ApiDefaultNameEnum.list.getCode().equals(action) ? FlagEnum.no.getCode()
							: FlagEnum.yes.getCode());
					req.setPath(path);
					for (ApiDefaultNameEnum defaultName : ApiDefaultNameEnum.values()) {
						if (defaultName.getCode().equals(action)
								&& req.getName().indexOf(defaultName.getName()) == -1) {
							req.setName(req.getName() + defaultName.getName());
						}
					}
					super.create(req);
				}
			}
		}
		// 更新生产环境接口版本
		this.configService.updateApiVersion_product();
		return Res.succ();
	}

	@Override
	@CacheEvict(value = RedisKeyEnum.PLATFORM_API_KEY, key = "#req.platform+':'+#req.path")
	public Res<?> update(Api req) {
		Res<?> res = super.update(req);
		// 更新生产环境接口版本
		this.configService.updateApiVersion_product();
		return res;
	}

	@Override
	@CacheEvict(value = RedisKeyEnum.PLATFORM_API_KEY, key = "#req.platform+':'+#req.path")
	public Res<?> delete(Api req) {
		Res<?> res = super.delete(req);
		// 更新生产环境接口版本
		this.configService.updateApiVersion_product();
		return res;
	}

	@Override
	@Cacheable(value = RedisKeyEnum.PLATFORM_API_KEY, key = "#platform+':'+#path", unless = "#result == null")
	public Api findByPlatformAndPath(String platform, String path) {
		return this.getOne(Wrappers.<Api> query().lambda().eq(Api::getPlatform, platform).eq(Api::getPath, path));
	}

	@Override
	public void writeProduct(Api req) {
		// 保存菜单
		refresh(req.getMenuList(), menuMapper);
		// 保存接口
		refresh(req.getApiList(), baseMapper);
		// 保存菜单接口关联
		refresh(req.getMenuApiList(), menuApiMapper);
		// 更新本地接口版本为远程版本
		this.configService.updateApiVersion_local(req.getVersion());
	}

	/**
	 * 更新最新的接口、菜单、权限
	 * 
	 * @param newList
	 * @param mapper
	 * @param <T>
	 */
	private <T extends Req> void refresh(List<T> newList, BaseMapper<T> mapper) {
		// 先删除再加入（达到覆盖的效果）
		List<T> oldList = mapper.selectList(null);
		if (CollUtil.isNotEmpty(oldList)) {
			mapper.deleteBatchIds(oldList.stream().map(a -> (Long) a.calId()).collect(Collectors.toList()));
		}
		for (T item : newList) {
			mapper.insert(item);
		}
	}
}
