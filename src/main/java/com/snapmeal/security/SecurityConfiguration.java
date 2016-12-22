package com.snapmeal.security;

import com.snapmeal.entity.User;
import com.snapmeal.repository.UserRepository;
import com.snapmeal.security.StatelessAuthenticationFilter;
import com.snapmeal.security.TokenAuthenticationService;
import com.snapmeal.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.Filter;

/**
 * Created by hristiyan on 20.12.16.
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private TokenAuthenticationService tokenAuthenticationService;


    public SecurityConfiguration() {
        super(true);
        this.userService = new UserService();
        tokenAuthenticationService = new TokenAuthenticationService("9SyECk96oDsTmXfogIieDI0cD/8Fp", userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()

                //allow anonymous resource requests
                .antMatchers("/").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/resources/**").permitAll()

                //allow anonymous POSTs to login
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()

                //allow anonymous GETs to API
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()

                //defined Admin only API area
                .antMatchers("/admin/**").hasRole("ADMIN")

                //all other request need to be authenticated
                .anyRequest().hasRole("USER").and()

                // custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
                .addFilterBefore(new StatelessLoginFilter("/api/login", tokenAuthenticationService, userService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)

                // custom Token based authentication based on the header previously given to the client
                .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserService userDetailsService() {
        return userService;
    }

    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        return tokenAuthenticationService;
    }
}
