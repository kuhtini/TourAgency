package com.tour.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private UserDetailsService userDetailsService;

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/tours/**").hasRole("STAFF")
//                .antMatchers(HttpMethod.PUT, "/api/tours/**").hasRole("STAFF")
//                .antMatchers(HttpMethod.PATCH, "/api/tours/**").hasRole("STAFF")
                .antMatchers(HttpMethod.DELETE, "/api/tours/**").hasRole("STAFF")
//
//                .antMatchers(HttpMethod.POST, "/api/groups/**").hasRole("STAFF")
//                .antMatchers(HttpMethod.PUT, "/api/groups/**").hasRole("STAFF")
//                .antMatchers(HttpMethod.PATCH, "/api/groups/**").hasRole("STAFF")
//                .antMatchers(HttpMethod.DELETE, "/api/groups/**").hasRole("STAFF")
//
//                .antMatchers(HttpMethod.POST, "/api/tourists/**").hasRole("STAFF")
//                .antMatchers(HttpMethod.PUT, "/api/tourists/**").hasRole("STAFF")
//                .antMatchers(HttpMethod.PATCH, "/api/tourists/**").hasRole("STAFF")
                .antMatchers(HttpMethod.DELETE, "/api/tourists/**").hasRole("STAFF")
//
//                .antMatchers(HttpMethod.POST, "/api/guides/**").hasRole("STAFF")
//                .antMatchers(HttpMethod.PUT, "/api/guides/**").hasRole("STAFF")
//                .antMatchers(HttpMethod.PATCH, "/api/guides/**").hasRole("STAFF")
               .antMatchers(HttpMethod.DELETE, "/api/guides/**").hasRole("STAFF")
                .and()
                .csrf().disable()
                .headers().frameOptions().sameOrigin();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

//    @Autowired
//    private BasicAuthenticationEntryPoint entryPoint;
//
//    @Autowired
//    private AccessDeniedHandler handler;
//
//
//    @Autowired
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/rest/tours/").permitAll()
//                .and()
//                .httpBasic().authenticationEntryPoint(entryPoint)
//                .and()
//                .exceptionHandling().accessDeniedHandler(handler);
//    }

}
