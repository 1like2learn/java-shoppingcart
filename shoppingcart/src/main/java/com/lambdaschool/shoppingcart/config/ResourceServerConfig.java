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

        resources.resourceId(RESOURCE_ID)
            .stateless(false);
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
            //Only Admins can alter user data
            .antMatchers(HttpMethod.POST, "/users/**")
            .hasAnyRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/users/**")
            .hasAnyRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/users/**")
            .hasAnyRole("ADMIN")
            .antMatchers(HttpMethod.GET, "users/users")
            .hasAnyRole("ADMIN")
            .antMatchers(HttpMethod.GET, "users/user/**")
            .hasAnyRole("ADMIN")
            .antMatchers("carts/cart/**")
            .hasAnyRole("ADMIN")
            .antMatchers("carts/create/**")
            .hasAnyRole("ADMIN")
            .antMatchers("products/**")
            .hasAnyRole("ADMIN")

            .antMatchers(HttpMethod.GET, "/carts/user")
            .hasAnyRole("ADMIN", "USER")
            .antMatchers(HttpMethod.POST, "/carts/create/product")
            .hasAnyRole("ADMIN", "USER")
            .antMatchers("/oauth/revoke-token", "/logout")
            .authenticated()
            .antMatchers("/roles/**")
            .hasAnyRole("ADMIN")
            .and()
            .exceptionHandling()
            .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
