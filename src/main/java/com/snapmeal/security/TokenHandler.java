package com.snapmeal.security;
;
import com.snapmeal.entity.jpa.User;
import com.snapmeal.service.UserService;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jersey.repackaged.com.google.common.base.Preconditions;

import java.sql.Time;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.UUID;

public final class TokenHandler {

    private final String secret;
    private final UserService userService;

    public TokenHandler(String secret, UserService userService) {
        this.secret = Base64.getEncoder().encodeToString(StringConditions.checkNotBlank(secret).getBytes());
        this.userService = Preconditions.checkNotNull(userService);
    }

    public JwtUser parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userService.loadUserByUsername(username);
    }

    public String createTokenForUser(JwtUser user) {
        Date now = new Date();
        System.out.println(new Date(now.getTime()).toString());
        Date expiration = new Date(now.getTime() + TimeUnit.HOURS.toMillis(1l));
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
