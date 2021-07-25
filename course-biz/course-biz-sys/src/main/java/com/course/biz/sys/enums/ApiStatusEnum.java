package com.course.biz.sys.enums;

import lombok.Getter;

/**
 * @author qinlei
 * @date 2021/6/28 下午12:30
 */
@Getter
public enum ApiStatusEnum {

    /** 上线 */
    online(1, "上线"),
    /** 下线 */
    downline(9, "下线");

    ApiStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;
    private String name;
}
