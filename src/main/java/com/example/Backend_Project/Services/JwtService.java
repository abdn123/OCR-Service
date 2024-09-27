package com.example.Backend_Project.Services;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.Backend_Project.Entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String jwtSigningKey;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }

    public Date extractExpiration(String token) { return extractClaim(token, Claims::getExpiration); }

    public boolean hasClaim(String token, String claimName) {
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) { return extractExpiration(token).before(new Date()); }

    public String generateToken(User user) {
        return createToken(user);
        
    }

    private String createToken(User user) {
        return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("authorities", user.getRole())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
            .signWith(SignatureAlgorithm.HS256, getSignInKey()).compact();
    }
 
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
