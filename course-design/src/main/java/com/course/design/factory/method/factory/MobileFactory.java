package com.course.design.factory.method.factory;

import com.course.design.factory.pojo.Mobile;
import com.course.design.factory.pojo.Product;

/**
 * @author qinlei
 * @date 2021/6/2 下午6:12
 */
public class MobileFactory extends ProductFactory {
    @Override
    public Product createProduct() {
        return  new Mobile();
    }
}
