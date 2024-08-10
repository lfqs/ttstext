package com.lfq.tts.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * 配置授权中心信息
 * @作者 lfq
 * @DATE 2024-08-02
 * current year
 **/
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * accessToken有效期 两小时
     */
    private int accessTokenValiditySeconds = 7200;

    /**
     * refreshToken有效期 两小时
     */
    private int refreshTokenValiditySeconds = 7200;

//    @Bean
//    public TokenStore tokenStore() {
//        // return new InMemoryTokenStore(); //使用内存中的 token store
//        return new JdbcTokenStore(dataSource); /// 使用Jdbctoken store
//    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //商户id
                .withClient("client_1")
                //商户secret
                .secret(passwordEncoder().encode("123456"))
                //回调地址
                .redirectUris("https://www.baidu.com/")
                /* OAuth2为我们提供了四种授权方式:
                 * 1、授权码模式（authorization code）用在客户端与服务端应用之间授权
                 * 2、简化模式（implicit）用在移动app或者web app(这些app是在用户的设备上的，如在手机上调起微信来进行认证授权)
                 * 3、密码模式（resource owner password credentials）应用直接都是受信任的(都是由一家公司开发的)
                 * 4、客户端模式（client credentials）用在应用API访问
                 */
                .authorizedGrantTypes("password", "client_credentials", "refresh_token", "authorization_code")
                //授权范围
                .scopes("all")
                //accessToken有效期
                .accessTokenValiditySeconds(accessTokenValiditySeconds)
                //refreshToken有效期
                .refreshTokenValiditySeconds(refreshTokenValiditySeconds);
    }

    /**
     * 设置token类型
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);
        endpoints.authenticationManager(authenticationManager());
        endpoints.userDetailsService(userDetailsService());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        // 允许表单认证
        oauthServer.allowFormAuthenticationForClients();
        // 允许check_token访问
        oauthServer.checkTokenAccess("permitAll()");
    }

    @Bean
    AuthenticationManager authenticationManager() {
        AuthenticationManager authenticationManager = new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return daoAuhthenticationProvider().authenticate(authentication);
            }

        };
        return authenticationManager;
    }

    @Bean
    public AuthenticationProvider daoAuhthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * 设置添加用户信息,正常应该从数据库中读取
     *
     * @return UserDetailsService
     */
    @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        userDetailsService.createUser(User.withUsername("user_1").password(passwordEncoder().encode("123456"))
                .authorities("ROLE_USER").build());
        userDetailsService.createUser(User.withUsername("user_2").password(passwordEncoder().encode("1234567"))
                .authorities("ROLE_USER").build());
        return userDetailsService;
    }


    /**
     * 设置加密方式
     *
     * @return PasswordEncoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
