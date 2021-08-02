package com.oskarskalski.cms.filters;

import com.oskarskalski.cms.configuration.JwtConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends OncePerRequestFilter {
    private final String authorizationTokenPrefix = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        if (authorizationHeader == null ||  authorizationHeader.length() < 5 || !authorizationHeader.startsWith(authorizationTokenPrefix)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String token = authorizationHeader.replace(authorizationTokenPrefix, "");

        try {

            JwtConfiguration jwtConfiguration = new JwtConfiguration();

            String username = jwtConfiguration.parse(token).getSubject();
//(List<Map<String, String>>) body.get("authorities")
//            var authorities = null;

//            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
//                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
//                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    null);

            SecurityContextHolder.getContext().setAuthentication(authentication);


        } catch (Exception e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
