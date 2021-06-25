package com.course.springboot.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.course.common.entity.Res;
import com.course.common.enums.RedisKeyEnum;
import com.course.mybatis.service.impl.UpServiceImpl;
import com.course.springboot.entity.Config;
import com.course.springboot.mapper.ConfigMapper;
import com.course.springboot.service.ConfigService;

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
		// Config req = this.baseMapper
		// .selectOne(Wrappers.<Config> query().lambda().eq(Config::getCode,
		// ConfigEnum.apiConfig.getCode()));
		// if (req != null) {
		// ApiConfig config = JsonUtil.jsonStringToBean(req.getValue(),
		// ApiConfig.class);
		// if (config != null) {
		// config.setVersionLocal(version);
		// req.setValue(JsonUtil.beanToJsonStringPretty(config));
		// this.update(req);
		// }
		// }
	}

	@Override
	@CacheEvict(value = RedisKeyEnum.CONFIG_KEY, key = "'" + RedisKeyEnum.CONFIG_APICONFIG_KEY + "'")
	public void updateApiVersion_product() {
		// Config req = this.baseMapper
		// .selectOne(Wrappers.<Config> query().lambda().eq(Config::getCode,
		// ConfigEnum.apiConfig.getCode()));
		// if (req != null) {
		// ApiConfig config = JsonUtil.jsonStringToBean(req.getValue(),
		// ApiConfig.class);
		// if (config != null &&
		// RunModeEnum.product.getCode().equals(config.getRunMode())) {
		// // 生产模式
		// config.setVersionProduct(DateUtil.format(new Date(),
		// "yyyyMMddHHmmssSSS"));
		// req.setValue(JsonUtil.beanToJsonStringPretty(config));
		// this.update(req);
		// }
		// }
	}
}
