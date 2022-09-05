package com.oskarskalski.cms.global.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.oskarskalski.cms.global.exception.AccessDeniedException;
import org.springframework.security.core.Authentication;


public class JwtConfiguration {
    public DecodedJWT parse(String token) {
        if(token.startsWith("Bearer "))
            token = token.replace("Bearer ", "");

        try {
            Algorithm algorithm = Algorithm.HMAC256(System.getenv("SECRET_KEY"));
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();

            return verifier.verify(token);
        } catch (JWTVerificationException exception){
            throw new AccessDeniedException();
        }
    }

    public String build(Authentication authResult) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(System.getenv("SECRET_KEY"));
            System.out.println(authResult.getName());
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("id", authResult.getName())
                    .withClaim("authorities", authResult.getAuthorities().toString())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new IllegalStateException();
        }
    }
}
