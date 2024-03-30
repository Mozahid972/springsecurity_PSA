package com.airbnb.service;

import com.airbnb.entity.PropertyUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.secret.key}")
    private String alorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expire-time}")
    private int expireTime;

    private Algorithm algorithm;

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(alorithmKey);
    }

    public String generateToken(PropertyUser user) {
        return JWT.create()
                .withClaim("USER_NAME",user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .withIssuer(issuer)
                .sign(algorithm);

    }

}
