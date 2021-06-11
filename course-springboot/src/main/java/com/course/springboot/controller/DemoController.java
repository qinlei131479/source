package com.course.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qinlei
 * @date 2021/6/10 下午2:53
 */
@RestController
public class DemoController {

    @RequestMapping("/demo")
    public String demo(){
        return "just demo test";
    }
}
