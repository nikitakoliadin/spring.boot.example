package com.qthegamep.admin.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Integer tokenValiditySeconds;
    private final AdminServerProperties adminServerProperties;

    public WebSecurityConfig(@Value("${token.validity.seconds}") Integer tokenValiditySeconds,
                             AdminServerProperties adminServerProperties) {
        this.tokenValiditySeconds = tokenValiditySeconds;
        this.adminServerProperties = adminServerProperties;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminServerProperties.getContextPath() + "/");
        httpSecurity.authorizeRequests()
                .antMatchers(adminServerProperties.getContextPath() + "/assets/**")
                .permitAll()
                .antMatchers(adminServerProperties.getContextPath() + "/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage(adminServerProperties.getContextPath() + "/login")
                .successHandler(successHandler)
                .and()
                .logout()
                .logoutUrl(adminServerProperties.getContextPath() + "/logout")
                .and()
                .httpBasic()
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers(
                        new AntPathRequestMatcher(adminServerProperties.getContextPath() + "/instances", HttpMethod.POST.toString()),
                        new AntPathRequestMatcher(adminServerProperties.getContextPath() + "/instances/*", HttpMethod.DELETE.toString()),
                        new AntPathRequestMatcher(adminServerProperties.getContextPath() + "/actuator/**"))
                .and()
                .rememberMe()
                .key(UUID.randomUUID().toString())
                .tokenValiditySeconds(tokenValiditySeconds);
    }
}
