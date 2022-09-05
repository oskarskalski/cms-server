package com.oskarskalski.cms.global.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oskarskalski.cms.global.exception.AccessDeniedException;
import com.oskarskalski.cms.global.configuration.JwtConfiguration;
import com.oskarskalski.cms.content.user.model.User;
import com.oskarskalski.cms.global.model.UsernameAndPasswordAuthenticationRequest;
import com.oskarskalski.cms.content.user.service.AttemptSignInService;
import com.oskarskalski.cms.content.user.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UserAuthenticationService userAuthenticationService;
    private final AttemptSignInService attemptSignInService;

    @Autowired
    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                UserAuthenticationService userAuthenticationService,
                                AttemptSignInService attemptSignInService) {
        this.authenticationManager = authenticationManager;
        this.userAuthenticationService = userAuthenticationService;
        this.attemptSignInService = attemptSignInService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);

            User user = userAuthenticationService
                    .findUserByEmail(authenticationRequest.getUsername());

            if (user != null) {
                if (!user.isSoftDelete()) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            user.getId(),
                            authenticationRequest.getPassword()
                    );

                    if (!attemptSignInService.attemptSignIn(user, authenticationRequest.getPassword()))
                        throw new AccessDeniedException();

                    Authentication authenticate = authenticationManager.authenticate(authentication);
                    return authenticate;
                } else
                    throw new AccessDeniedException();
            } else {
                throw new AccessDeniedException();
            }
        } catch (IOException e) {
            throw new AccessDeniedException();
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        JwtConfiguration jwtConfiguration = new JwtConfiguration();
        String token = jwtConfiguration.build(authResult);
        response.addHeader("Authorization", "Bearer " + token);
    }
}
