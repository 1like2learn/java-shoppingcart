
//package com.lambdaschool.shoppingcart.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//
///**
// * Sets up an Authorization Server
// * grants authorization
// * generates access tokens
// **/
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//
//    //username for frontend app
//    static final String CLIENT_ID = "lambda-id";
//    //password for frontend app
//    static final String CLIENT_SECRET = "lambda-secret";
//    //thing needed to gain auth
//    static final String GRANT_TYPE_PASSWORD = "password";
//    //code type used to verify authorization
//    static final String AUTHORIZATION_CODE = "authorization_code";
//    //allows users to read
//    static final String SCOPE_READ = "read";
//    //allows users to write
//    static final String SCOPE_WRITE = "write";
//    //tells the framework that users are trusted,
//    //granting them some kind of increased access
//    static final String SCOPE_TRUST = "trust";
//    //limits the amount of time a user is trusted.
//    //its counted in seconds. -1 means the auth will
//    //not be revoked due to time
//    static final int ACCESS_TOKEN_VALIDITY_SECONDS = -1;
//
//    //token store is setup in SecurityConfig but managed here
//    @Autowired
//    private TokenStore tokenStore;
//
//    //where the users and their tokens are stored and managed
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    //encrypts client secret/password
//    @Autowired
//    private PasswordEncoder encoder;
//
//    //takes the info we made up there^ and converts it into a form that Spring will
//    //manage for us
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//
//        clients.inMemory()
//            .withClient(CLIENT_ID)
//            .secret(encoder.encode(CLIENT_SECRET))
//            .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE)
//            .scopes(SCOPE_READ, SCOPE_TRUST, SCOPE_WRITE)
//            .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//
//        endpoints.tokenStore(tokenStore)
//            .authenticationManager(authenticationManager);
//        endpoints.pathMapping("oauth/token", "/login");
//    }
//}
