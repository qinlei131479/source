//package com.course.oauth2.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//
///**
// * Created on 2018/1/17.
// *
// * @author zlf
// * @since 1.0
// */
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    /**
//     * 自定义登录成功处理器
//     */
////    @Autowired
////    private AuthenticationSuccessHandler appLoginInSuccessHandler;
////
////    @Autowired
////    private AuthenticationFailureHandler appLoginFailureHandler;
//
////    @Autowired
////    private PermitAllSecurityConfig permitAllSecurityConfig;
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//
//        http
//                // 此处不要禁止formLogin,code模式测试需要开启表单登陆,并且/oauth/token不要放开或放入下面ignoring,因为获取token首先需要登陆状态
//                .formLogin().loginPage("/authentication/require").loginProcessingUrl("/authentication/form").and()
//                .csrf().disable()
//
//                .authorizeRequests().antMatchers("/test", "/authentication/require", "/ologin").permitAll().and()
//                .authorizeRequests().anyRequest().authenticated();
//
//        // @formatter:ON
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.authenticationEntryPoint(new AuthExceptionEntryPoint());
//    }
//}
