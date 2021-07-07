package com.course.springboot.service;

import com.course.common.mybatis.service.UpService;
import com.course.springboot.entity.Config;

/**
 * service接口：参数配置表
 *
 * @author qinlei
 * @date   2021/06/23 16:22
 */
public interface ConfigService extends UpService<Config> {

    /**
     * 系统配置查询，通过编号
     *
     * @param code
     * @return
     */
    String findConfigValueByCode(String code);

    /**
     * 更新本地环境（非生产环境）接口版本
     *
     * @param version
     */
    void updateApiVersion_local(String version);

    /**
     * 更新生产环境接口版本
     */
    void updateApiVersion_product();
}
