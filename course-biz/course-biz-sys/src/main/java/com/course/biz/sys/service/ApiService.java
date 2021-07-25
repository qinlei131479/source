package com.course.biz.sys.service;

import com.course.common.mybatis.service.UpService;
import com.course.biz.sys.entity.Api;

/**
 * service接口：应用接口表
 *
 * @author qinlei
 * @date   2021/06/28 12:13
 */
public interface ApiService extends UpService<Api> {

    /**
     * 根据平台和路径查询接口
     *
     * @param platform
     * @param path
     * @return
     */
    Api findByPlatformAndPath(String platform, String path);

    /**
     * 写入线上的接口配置
     *
     * @param req
     */
    void writeProduct(Api req);
}
