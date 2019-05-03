package com.city.sso.ssoserver.server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


//这个是oauth登录的必需配置
@Configuration
@EnableAuthorizationServer
public class SsoAuthenticationServerConfig extends AuthorizationServerConfigurerAdapter {


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("city1")
                .secret("cityscape1")
                //使用授权码和refresh_token
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                .scopes("all")
                .and()
                .withClient("city2")
                .secret("cityscape2")
                //使用授权码和refresh_token
                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                .scopes("all");


//        clients.inMemory()
//                .withClient("fbed1d1b4b1449daa4bc49397cbe2350")
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//                .scopes("read","write")
//                .secret("fbed1d1b4b1449daa4bc49397cbe2350")
//                .accessTokenValiditySeconds(120)//Access token is only valid for 2 minutes.
//                .refreshTokenValiditySeconds(600)//Refresh token is only valid for 10 minutes.
//                .redirectUris("http://localhost:8080/session");


    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        /**
         * 这里用tokenKey(就是签名city)进行比对认证
         * 默认tokenKey是denyAll(),就拒绝所用
         * 所以我们配置isAuthenticated()来表明我们可以经过认证通过服务器
         */
        security.tokenKeyAccess("isAuthenticated()");
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 这个会用一个秘钥来签名 和这个签名对应对的请求我们才去处理
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //注意:我们这里是硬编码 当然你也可以试着用其他的方法来添加签名
        converter.setSigningKey("city");
        return converter;
    }


}
