package com.dev.connect.jwt;




import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private final String SECRET_KEY="qazxswedcvfrtgbnhyujmkiolpoiuytrewqsdfghjklkmnbvcx";

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*60*60))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims getClaimsFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserNameFromToken(String token){
        return getClaimsFromToken(token).getSubject();
    }

    public Date getExpirationFromToken(String token){
        return getClaimsFromToken(token).getExpiration();
    }

    public boolean isTokenExpire(String token){
        return getExpirationFromToken(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String userNameFromToken = getUserNameFromToken(token);
        return userNameFromToken.equals(userDetails.getUsername()) && !isTokenExpire(token);
    }

}
