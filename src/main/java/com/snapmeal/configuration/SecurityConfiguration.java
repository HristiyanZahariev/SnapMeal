package com.snapmeal.configuration;

import com.snapmeal.security.StatelessAuthenticationFilter;
import com.snapmeal.security.StatelessLoginFilter;
import com.snapmeal.security.TokenAuthenticationService;
import com.snapmeal.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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
    @CrossOrigin(origins = "http://localhost:3000")
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .authorizeRequests()

                //allow anonymous resource requests
                .antMatchers("/").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/resources/**").permitAll()

                //allow anonymous POSTs to login
                .antMatchers(HttpMethod.POST, "/login").permitAll()

                //allow anonymous GETs to Site
                .antMatchers(HttpMethod.GET, "/**").permitAll()

                //defined Admin only API area
                .antMatchers("/admin/**").hasRole("ADMIN")


                //all other request need to be authenticated
                .anyRequest().hasRole("USER").and()

                // custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
                .addFilterBefore(new StatelessLoginFilter("/login", tokenAuthenticationService, userService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)

                // custom Token based authentication based on the header previously given to the client
                .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/user/register");
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

    //used for enabling cors
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
