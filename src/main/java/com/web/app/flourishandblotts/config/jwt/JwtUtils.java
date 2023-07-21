package com.web.app.flourishandblotts.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;


    /**
     * For generating access tokens
     * */
    public String generateAccessToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis()+Long.parseLong(this.timeExpiration))))
                .signWith(this.getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /*
    * Obtain the username from a token.
    * */
    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    /*
    * Validate the access token
     */
    public boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSignatureKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return true;
        }catch (Exception e){
            log.error("Invalid Token: "+ e.getMessage());
            return false;
        }
    }

    /*
    * Obtain a single claim
    *
    * */
    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction){
        Claims claims = this.extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    /*
    * Obtain all claims of the token
    * */
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    /*
    * Obtaining the token signature
    * */
    public Key getSignatureKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
