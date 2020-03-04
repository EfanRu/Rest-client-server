package com.example.resttemplate.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
        auth
                .inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic();
//                .authorizeRequests()
//                    .antMatchers("/admin/**").hasRole("ADMIN")
//                    .antMatchers("/user/**").authenticated()
//                    .antMatchers("/login").anonymous()
//                .and().formLogin()
//                    .loginPage("/login")
//                    .loginProcessingUrl("/authorization")
//                    .successHandler(myAuthenticationSuccessHandler())
//                .and().logout()
//                    .permitAll()
//                    .logoutUrl("/logout")
//                    .logoutSuccessUrl("/login")
//                    .invalidateHttpSession(true)
//                    .deleteCookies();
    }
}
