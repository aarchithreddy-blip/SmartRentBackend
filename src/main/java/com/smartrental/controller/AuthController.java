package com.smartrental.controller;
import com.smartrental.dto.*;
import com.smartrental.model.*;
import com.smartrental.repository.UserRepository;
import com.smartrental.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users;private final PasswordEncoder encoder;private final JwtService jwt;
 public AuthController(UserRepository users,PasswordEncoder encoder,JwtService jwt){this.users=users;this.encoder=encoder;this.jwt=jwt;}
 @PostMapping("/register") public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest r){
  String email=r.email().trim().toLowerCase();
  if(users.existsByEmail(email))return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message","An account with this email already exists."));
  AppUser u=new AppUser();u.setName(r.name().trim());u.setEmail(email);u.setPasswordHash(encoder.encode(r.password()));u.setPhone(r.phone());u.setRole(r.role()==null?Role.TENANT:r.role());users.save(u);
  return ResponseEntity.status(HttpStatus.CREATED).body(response(u));
 }
 @PostMapping("/login") public ResponseEntity<?> login(@Valid @RequestBody AuthRequest r){
  AppUser u=users.findByEmail(r.email().trim().toLowerCase()).orElse(null);
  if(u==null||!encoder.matches(r.password(),u.getPasswordHash()))return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","Invalid email or password."));
  return ResponseEntity.ok(response(u));
 }
 private AuthResponse response(AppUser u){return new AuthResponse(jwt.generate(u.getEmail()),u.getId(),u.getName(),u.getEmail(),u.getRole());}
}
