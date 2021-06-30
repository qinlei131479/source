package com.course.common.mybatis.resource.handler;

import com.course.common.mybatis.resource.sqlsession.ParameterMapping;
import com.course.common.mybatis.resource.utils.TokenHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理#{}中的参数的
 * @author qinlei
 * @date 2021/5/31 下午3:52
 */
public class ParameterMappingTokenHandler implements TokenHandler {

    private List<ParameterMapping> parameterMappings = new ArrayList<>();

    @Override
    public String handleToken(String content) {
        parameterMappings.add(buildParameterMapping(content));
        return "?";
    }

    private ParameterMapping buildParameterMapping(String content) {
        ParameterMapping parameterMapping = new ParameterMapping(content);
        return parameterMapping;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
