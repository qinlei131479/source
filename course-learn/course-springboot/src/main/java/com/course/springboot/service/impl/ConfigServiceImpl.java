package com.course.springboot.service.impl;

import java.util.Date;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.course.common.core.entity.Res;
import com.course.common.core.enums.RedisKeyEnum;
import com.course.common.mybatis.service.impl.UpServiceImpl;
import com.course.springboot.entity.Config;
import com.course.springboot.entity.config.ApiConfig;
import com.course.springboot.enums.ApiRunModeEnum;
import com.course.springboot.enums.ConfigEnum;
import com.course.springboot.mapper.ConfigMapper;
import com.course.springboot.service.ConfigService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;

/**
 * service实现类：参数配置表
 *
 * @author qinlei
 * @date 2021/06/23 16:22
 */
@Service
public class ConfigServiceImpl extends UpServiceImpl<ConfigMapper, Config> implements ConfigService {

	@Override
	@Cacheable(value = RedisKeyEnum.CONFIG_KEY, key = "#code", unless = "#result == null")
	public String findConfigValueByCode(String code) {
		Config config = this.baseMapper.selectOne(Wrappers.<Config> query().lambda().eq(Config::getCode, code));
		return config == null ? null : config.getValue();
	}

	@Override
	@CacheEvict(value = RedisKeyEnum.CONFIG_KEY, key = "#req.code")
	public Res<?> create(Config req) {
		this.checkFieldRepeatCreate(req, "code:编号");
		return super.create(req);
	}

	@Override
	@CacheEvict(value = RedisKeyEnum.CONFIG_KEY, key = "#req.code")
	public Res<?> update(Config req) {
		// 检查属性重复
		this.checkFieldRepeatUpdate(req, "code:编号");
		return super.update(req);
	}

	@Override
	@CacheEvict(value = RedisKeyEnum.CONFIG_KEY, allEntries = true)
	public Res<?> delete(Config req) {
		Config old = this.baseMapper.selectById(req.getId());
		if (old != null) {
			super.delete(req);
		}
		return Res.succ();
	}

	@Override
	@CacheEvict(value = RedisKeyEnum.CONFIG_KEY, key = "'" + RedisKeyEnum.CONFIG_APICONFIG_KEY + "'")
	public void updateApiVersion_local(String version) {
		Config req = this.baseMapper
				.selectOne(Wrappers.<Config> query().lambda().eq(Config::getCode, ConfigEnum.apiConfig.getCode()));
		if (req != null) {
			ApiConfig config = JSONUtil.toBean(req.getValue(), ApiConfig.class);
			if (config != null) {
				config.setVersionLocal(version);
				req.setValue(JSONUtil.toJsonPrettyStr(config));
				this.update(req);
			}
		}
	}

	@Override
	@CacheEvict(value = RedisKeyEnum.CONFIG_KEY, key = "'" + RedisKeyEnum.CONFIG_APICONFIG_KEY + "'")
	public void updateApiVersion_product() {
		Config req = this.baseMapper
				.selectOne(Wrappers.<Config> query().lambda().eq(Config::getCode, ConfigEnum.apiConfig.getCode()));
		if (req != null) {
			ApiConfig config = JSONUtil.toBean(req.getValue(), ApiConfig.class);
			if (config != null && ApiRunModeEnum.product.getCode().equals(config.getRunMode())) {
				// 生产模式
				config.setVersionProduct(DateUtil.format(new Date(), "yyyyMMddHHmmssSSS"));
				req.setValue(JSONUtil.toJsonPrettyStr(config));
				this.update(req);
			}
		}
	}
}
