package com.airbnb.service;

import com.airbnb.entity.PropertyUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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

    private final static String USER_NAME = "username";

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(alorithmKey);
    }

    public String generateToken(PropertyUser user) {
        return JWT.create()
                .withClaim(USER_NAME,user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .withIssuer(issuer)
                .sign(algorithm);

    }

    //verify token and return username is valid
    public String getUserName(String token) {
        //rozi with  bunee vee
        DecodedJWT decodedJwt =JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJwt.getClaim(USER_NAME).asString();
    }

}
