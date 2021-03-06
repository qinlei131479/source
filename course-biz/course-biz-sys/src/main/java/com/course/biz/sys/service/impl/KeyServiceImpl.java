package com.course.biz.sys.service.impl;

import com.course.common.core.redis.RedisUtil;
import org.springframework.stereotype.Service;

import com.course.biz.sys.entity.Key;
import com.course.biz.sys.mapper.KeyMapper;
import com.course.biz.sys.service.KeyService;
import com.course.common.core.enums.FlagEnum;
import com.course.common.mybatis.service.impl.UpServiceImpl;

import lombok.RequiredArgsConstructor;

/**
 * 全局唯一key生成
 * 
 * @author qinlei
 * @date 2021/6/25 下午5:47
 */
@Service
@RequiredArgsConstructor
public class KeyServiceImpl extends UpServiceImpl<KeyMapper, Key> implements KeyService {

	private final RedisUtil redisUtil;

	@Override
	public Long updateKey() {
		Key key = new Key();
		baseMapper.updateKey(key);
		return key.getId();
	}

	@Override
	public Long prepare(Key req) {
		if (req != null && FlagEnum.checkYes(req.getEnableFlag())) {
			Long size = this.redisUtil.keySize();
			if (size < req.getMin()) {
				Long createNum = req.getMax() - size;
				for (int i = 0; i < createNum; i++) {
					this.redisUtil.keyRightPush(this.updateKey());
				}
				return createNum;
			} else {
				return 0L;
			}
		}
		return -1L;
	}
}
