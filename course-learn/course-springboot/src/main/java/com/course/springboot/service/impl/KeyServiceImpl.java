package com.course.springboot.service.impl;

import org.springframework.stereotype.Service;

import com.course.common.component.RedisUtil;
import com.course.common.enums.FlagEnum;
import com.course.mybatis.service.impl.UpServiceImpl;
import com.course.springboot.entity.Key;
import com.course.springboot.mapper.KeyMapper;
import com.course.springboot.service.KeyService;

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
