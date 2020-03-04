package com.tz.monitor.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * 安全配置
 * @author KyrieCao
 * @version v1.0.0
 * @date 2020/3/4 17:21 21
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String adminContextPath;

    public SecurityConfig(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTO");
        successHandler.setDefaultTargetUrl(adminContextPath + "/");
        http.authorizeRequests().
                antMatchers(adminContextPath + "/assets/**", adminContextPath + "/login").permitAll()
                .anyRequest().authenticated().and().formLogin().loginPage(adminContextPath + "/login")
                .successHandler(successHandler).and().logout().logoutUrl(adminContextPath + "/logout").and().httpBasic()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/instances", "/actuator/**", adminContextPath + "/logout");
    }
}
