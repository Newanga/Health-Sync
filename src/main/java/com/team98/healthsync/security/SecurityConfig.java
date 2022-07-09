package com.team98.healthsync.security;

import com.team98.healthsync.config.CustomLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomLoginSuccessHandler sucessHandler;

    @Autowired
    RememberMeServices rememberMeServices;

    public SecurityConfig() {
        super();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/assets/**").permitAll()
                .antMatchers("admin/**", "thymeleaf/admin/**").access("hasRole('ADMIN')")
                .antMatchers("doctor/**", "thymeleaf/doctor/**").access("hasRole('DOCTOR')")
                .antMatchers("receptionist/**", "thymeleaf/receptionist/**").access("hasRole('RECEPTIONIST')")
                .antMatchers("labtechnician/**", "thymeleaf/labtechnician/**").access("hasRole('LABTECHNICIAN')")
                .antMatchers("pharmacist/**", "thymeleaf/pharmacist/**").access("hasRole('PHARMACIST')")
                .anyRequest()
                .authenticated()
                .and()

                //login
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .permitAll()
                .successHandler(sucessHandler)
                .and()
                .rememberMe()
                    .rememberMeServices(rememberMeServices)
                    .key("rxf^&YGWe7d8buiewdcscfe4qrdh743bfq")

                .and()
                //logout
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "rememberme")
                .logoutSuccessUrl("/login");


    }

    @Bean
    public TokenBasedRememberMeServices  getPersistentTokenBasedRememberMeServices() {
        TokenBasedRememberMeServices  tokenBasedservice = new TokenBasedRememberMeServices("rxf^&YGWe7d8buiewdcscfe4qrdh743bfq", userDetailsService);
        tokenBasedservice.setCookieName("rememberme");
        tokenBasedservice.setTokenValiditySeconds(60 * 60 * 24);
        tokenBasedservice.setAlwaysRemember(true);
        tokenBasedservice.setUseSecureCookie(true);
        return tokenBasedservice;
    }

}
