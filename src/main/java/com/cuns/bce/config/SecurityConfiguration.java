package com.cuns.bce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration implements WebMvcConfigurer {
    public static final AntPathRequestMatcher[] ENDPOINTS_WHITELIST = {
            new AntPathRequestMatcher("/css/**"),
            new AntPathRequestMatcher("/js/**"),
            new AntPathRequestMatcher("/images/**"),
            new AntPathRequestMatcher("/webfonts/**"),
            new AntPathRequestMatcher("/"),
            new AntPathRequestMatcher("/login"),
            new AntPathRequestMatcher("/register"),
            new AntPathRequestMatcher("/forgot-password"),
            new AntPathRequestMatcher("/comics/**"),
            new AntPathRequestMatcher("/crawl/**"),
            new AntPathRequestMatcher("/profiles/**"),
            // api
            new AntPathRequestMatcher("/api/comics/search"),
    };
    public static final AntPathRequestMatcher[] ENDPOINTS_WHITELIST_AUTHENTICATED = {
            new AntPathRequestMatcher("/comics/**/chapter/**"),
            new AntPathRequestMatcher("/comics/likes"),
            new AntPathRequestMatcher("/profiles/**/follows"),
    };
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String LOGIN_FAIL_URL = LOGIN_URL + "?error";
    public static final String DEFAULT_SUCCESS_URL = "/";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(request ->
                        request.requestMatchers(ENDPOINTS_WHITELIST_AUTHENTICATED).authenticated()
                                .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                                .anyRequest().authenticated())
                .csrf(c -> c.disable())
                .formLogin(form -> form
                        .loginPage(LOGIN_URL)
                        .loginProcessingUrl(LOGIN_URL)
                        .failureUrl(LOGIN_FAIL_URL)
                        .usernameParameter(USERNAME)
                        .passwordParameter(PASSWORD)
                        .defaultSuccessUrl(DEFAULT_SUCCESS_URL))
                .logout(logout -> logout
                        .logoutUrl(LOGOUT_URL)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl(LOGIN_URL + "?logout"))
                .rememberMe(rememberMe -> rememberMe
                        .key("bcunsentertainment")
                        // tokenValiditySeconds = 1 month
                        .tokenValiditySeconds(2592000));


//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//                        .invalidSessionUrl("/invalidSession.htm")
//                        .maximumSessions(1)
//                        .maxSessionsPreventsLogin(true));
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // addResourceHandlers
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("/webfonts/**")
                .addResourceLocations("classpath:/static/webfonts/")
                .setCachePeriod(31556926);
    }
}
