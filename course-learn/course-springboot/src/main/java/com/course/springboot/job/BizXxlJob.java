package com.course.springboot.job;

import java.util.List;

import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.course.common.entity.Res;
import com.course.common.enums.RedisKeyEnum;
import com.course.common.utils.SpringContextHolder;
import com.course.springboot.entity.ApiLog;
import com.course.springboot.entity.Key;
import com.course.springboot.service.ApiLogService;
import com.course.springboot.service.KeyService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * xxl-job 真正的定时任务执行方法
 * 
 * @author qinlei
 * @date 2021/6/30 下午5:17
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BizXxlJob {

	private final KeyService keyService;

	public void init() {
		log.info("init");
	}

	public void destroy() {
		log.info("destory");
	}

	@XxlJob(value = "testHandler", init = "init", destroy = "destroy")
	public ReturnT<String> test(String param) throws Exception {
		Long num = 10L;
		log.info("test-创建主键id，数量={}", num);
		return new ReturnT(ReturnT.SUCCESS_CODE, "test-创建主键数量=" + num);
	}

	/**
	 * 定时生成主键（Bean模式）
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@XxlJob(value = "keyHandler", init = "init", destroy = "destroy")
	public ReturnT<String> key(String param) throws Exception {
		Key req = JSONUtil.toBean(param, Key.class);
		Long num = keyService.prepare(req);
		if (num>0){
			log.info("创建主键id，数量={}", num);
		}
		return new ReturnT(ReturnT.SUCCESS_CODE, "创建主键数量=" + num);
	}

	/**
	 * 定时存储接口日志（Bean模式）
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@XxlJob(value = "apiLogHandler", init = "init", destroy = "destroy")
	public ReturnT<String> apiLog(String param) throws Exception {
		RedisTemplate<String, String> redisTemplate = SpringContextHolder.getBean("redisTemplate");
		ApiLogService apiLogService = SpringContextHolder.getBean(ApiLogService.class);
		String key = RedisKeyEnum.apilog_list.getKey();
		BoundListOperations<String, String> ops = redisTemplate.boundListOps(key);
		List<String> val;
		int num = 0;
		while (CollUtil.isNotEmpty((val = ops.range(0, 1)))) {
			String apiLogStr = val.get(0);
			ApiLog apiLog = JSONUtil.toBean(apiLogStr, ApiLog.class);
			if (apiLog.getId() == null) {
				// 新日志（不是修改日志）
				Res<?> res = apiLogService.create(apiLog);
				// apiLog = (ApiLog) res.getObj();
			}
			String apiLogStr2 = ops.leftPop();
			if (apiLogStr.equals(apiLogStr2)) {
				num++;
				log.debug("完成处理的apilog，{}", apiLogStr2);
			} else if (StrUtil.isNotBlank(apiLogStr2)) {
				log.error("失败处理的apilog，退回，{}", apiLogStr2);
				ops.rightPush(apiLogStr2);
			}
		}
		return new ReturnT(ReturnT.SUCCESS_CODE, "存储接口日志数量=" + num);
	}

}
