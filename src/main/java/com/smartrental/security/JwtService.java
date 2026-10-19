package com.smartrental.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
@Service
public class JwtService {
 private final SecretKey key; private final long expiration;
 public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms}") long expiration){
  if(secret.getBytes(StandardCharsets.UTF_8).length<32) throw new IllegalArgumentException("APP_JWT_SECRET must be at least 32 bytes");
  this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));this.expiration=expiration;
 }
 public String generate(String email){Date now=new Date();return Jwts.builder().subject(email).issuedAt(now).expiration(new Date(now.getTime()+expiration)).signWith(key).compact();}
 public String extractEmail(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
 public boolean valid(String token){try{return extractEmail(token)!=null;}catch(JwtException|IllegalArgumentException e){return false;}}
}
