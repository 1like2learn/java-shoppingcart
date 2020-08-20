package com.lambdaschool.shoppingcart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**After the frontend application has been auhorized
 * the user needs to be authenticated
 * This class also controls which users
 * have control over which endpoints**/
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource_id";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            //grants access to the endpoints by anyone
            // and everyone who has been authorized
            .antMatchers("/",
                "/h2-console/**",
                "/swagger-resources/**",
                "/swagger-resource/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/webjars/**",
                "/createnewuser")
            .permitAll()
            .antMatchers("/users/**",
                "/products/**",
                "/carts/**",
                "/roles/**")
            .hasAnyRole("ADMIN")
            //users and admins can access these endpoints
            .antMatchers(HttpMethod.GET, "/carts/user")
            .hasAnyRole("USER")
            .antMatchers(HttpMethod.POST, "/carts/create/product")
            .hasAnyRole("USER")
            .antMatchers("/users/myinfo")
            .hasAnyRole("USER")
            .antMatchers("/oauth/revoke-token", "/logout")
            .authenticated()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
