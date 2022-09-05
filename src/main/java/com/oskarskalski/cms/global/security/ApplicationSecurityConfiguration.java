package com.oskarskalski.cms.global.security;

import com.oskarskalski.cms.global.filters.AuthenticationFilter;
import com.oskarskalski.cms.global.filters.AuthorizationFilter;
import com.oskarskalski.cms.content.user.repo.UserRepo;
import com.oskarskalski.cms.content.user.service.AttemptSignInService;
import com.oskarskalski.cms.content.user.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final UserAuthenticationProvider userAuthenticationProvider;
    private final UserAuthenticationService userAuthenticationService;
    private final AttemptSignInService attemptSignInService;

    @Autowired
    public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder,
                                            UserRepo userRepo,
                                            UserAuthenticationProvider userAuthenticationProvider,
                                            UserAuthenticationService userAuthenticationService,
                                            AttemptSignInService attemptSignInService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.userAuthenticationProvider = userAuthenticationProvider;
        this.userAuthenticationService = userAuthenticationService;
        this.attemptSignInService = attemptSignInService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new AuthorizationFilter(), AuthenticationFilter.class)
                .addFilter(new AuthenticationFilter(authenticationManager(), userAuthenticationService, attemptSignInService))
                .authorizeRequests()
                .antMatchers("/api/users/add", "/login", "/api/users/fullName/*").permitAll()
                .anyRequest()
                .authenticated();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.addExposedHeader("Authorization");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userAuthenticationProvider);
    }
}
