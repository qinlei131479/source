package com.course.biz.sys.enums;

import lombok.Getter;

/**
 * @author qinlei
 * @date 2021/6/28 下午3:23
 */
@Getter
public enum ApiDefaultNameEnum {

    /** 查询 */
    list("查询"),
    /** 添加 */
    create("添加"),
    /** 修改 */
    update("修改"),
    /** 删除 */
    delete("删除");

    ApiDefaultNameEnum(String name) {
        this.code = this.name();
        this.name = name;
    }

    private String code;
    private String name;
}
