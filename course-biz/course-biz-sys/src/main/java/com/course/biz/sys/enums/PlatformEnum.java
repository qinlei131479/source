package com.course.biz.sys.enums;

import lombok.Getter;

/**
 * @author qinlei
 * @date 2021/6/28 下午12:24
 */
@Getter
public enum PlatformEnum {
    /** 后台 */
    back("后台"),
    /** 前台 */
    front("前台"),
    /** 中台 */
    middle("中台");

    PlatformEnum(String name) {
        this.code = this.name();
        this.name = name;
    }

    private String code;
    private String name;
}
