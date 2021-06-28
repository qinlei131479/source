package com.course.springboot.enums;

import com.course.common.enums.RedisKeyEnum;
import lombok.Getter;

/**
 * @author qinlei
 * @date 2021/6/28 下午3:39
 */
@Getter
public enum  ConfigEnum {

    /** 附件校验配置 */
    attachValid("附件校验配置"),
    /** 附件配置 */
    attachConfig("附件配置"),
    /** 接口配置 */
    apiConfig(RedisKeyEnum.CONFIG_APICONFIG_KEY, "接口配置"),
    /** 菜单配置 */
    menuConfig("菜单配置"),
    /** 腾讯云oss配置 */
    qcloudOssConfig("腾讯云oss配置"),
    /** 阿里云oss配置 */
    acloudOssConfig("阿里云oss配置"),
    /** ueditor编辑器配置 */
    ueditorConfig("ueditor编辑器配置");

    ConfigEnum(String name) {
        this.code = this.name();
        this.name = name;
    }

    ConfigEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;
}
