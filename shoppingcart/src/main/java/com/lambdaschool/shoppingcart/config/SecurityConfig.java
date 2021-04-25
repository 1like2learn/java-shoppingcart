package com.lambdaschool.shoppingcart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.annotation.Resource;

/**Encodes passwords, stores tokens, and implements users**/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Lets us customize the auth manager but the defaults are usually fine
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }

    //connects Spring's user details to our implementation
    @Resource(name = "securityUserService")
    private UserDetailsService userDetailsService;

    //ties our user implemention and password encoding to the auth manager
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception{

        auth.userDetailsService(userDetailsService)
        .passwordEncoder(encoder());
    }

    //where we store the tokens
    @Bean
    public TokenStore tokenStore(){

        return new InMemoryTokenStore();
    }

    //method of encoding passwords
    @Bean
    public PasswordEncoder encoder(){

        return new BCryptPasswordEncoder();
    }
}
