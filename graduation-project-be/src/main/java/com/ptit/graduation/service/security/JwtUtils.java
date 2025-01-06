package com.ptit.graduation.service.security;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private long expiration;

  @Value("${jwt.refreshExpiration}")
  private long refreshExpiration;  // Thời gian hết hạn của refresh token (ms)


  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
  }

  public String generateToken(String username) {
    return Jwts.builder()
          .setSubject(username)
          .setIssuedAt(new Date())
          .setExpiration(new Date(System.currentTimeMillis() + expiration))
          .signWith(getSigningKey(), SignatureAlgorithm.HS256)
          .compact();
  }
  // Tạo Refresh Token
  public String generateRefreshToken(String username) {
    return Jwts.builder()
          .setSubject(username)
          .setIssuedAt(new Date())
          .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration)) // Hết hạn sau thời gian refreshExpiration
          .signWith(getSigningKey(), SignatureAlgorithm.HS256)
          .compact();
  }

  // giải mã token
  public String extractUsername(String token) {
    return Jwts.parserBuilder()
          .setSigningKey(getSigningKey())
          .build()
          .parseClaimsJws(token)
          .getBody()
          .getSubject();
  }

  // check valid token
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  // check hết hạn của token
  private boolean isTokenExpired(String token) {
    return Jwts.parserBuilder()
          .setSigningKey(getSigningKey())
          .build()
          .parseClaimsJws(token)
          .getBody()
          .getExpiration()
          .before(new Date());
  }

  // Validate refresh token specifically
  public boolean isRefreshTokenValid(String token) {
    try {
      // Validate token signature and expiration
      Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }

  // Lenient access token validation for refresh
  public boolean isAccessTokenValidForRefresh(String token, String expectedUsername) {
    try {
      // Extract username and compare
      String username = extractUsername(token);
      return username.equals(expectedUsername);
    } catch (ExpiredJwtException e) {
      // Allow expired access token during refresh
      String username = e.getClaims().getSubject();
      return username.equals(expectedUsername);
    } catch (JwtException e) {
      return false;
    }
  }
}
