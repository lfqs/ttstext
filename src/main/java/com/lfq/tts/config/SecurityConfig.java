package com.lfq.tts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

/**
 * 添加Security权限配置
 * @作者 lfq
 * @DATE 2024/8/10 15:24
 * current year
 **/
@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     *  授权中心管理器
     * @return AuthenticationManager
     * @throws Exception 异常
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     *  拦截所有请求,使用httpBasic方式登陆
     * @param http 请求
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").fullyAuthenticated().and().httpBasic();
    }

}
