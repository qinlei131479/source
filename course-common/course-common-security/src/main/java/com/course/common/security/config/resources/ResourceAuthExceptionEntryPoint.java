package com.course.common.security.config.resources;

import cn.hutool.http.HttpStatus;
import com.course.common.core.entity.Res;
import com.course.common.core.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  客户端异常处理
 * @author qinlei
 * @date 2021/8/7 下午6:35
 */
@Slf4j
@Component
public class ResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
        Res res = Res.noLogin();
        res.setDetail(authException.getMessage());
        WebUtil.responseWriteJson(response, res);
    }
}
