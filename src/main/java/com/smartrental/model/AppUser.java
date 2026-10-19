package com.smartrental.model;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="app_users", uniqueConstraints=@UniqueConstraint(columnNames="email"))
public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=100) private String name;
 @Column(nullable=false,unique=true,length=190) private String email;
 @Column(nullable=false) private String passwordHash;
 @Column(length=30) private String phone;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private Role role=Role.TENANT;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
 public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
 public Role getRole(){return role;} public void setRole(Role v){role=v;}
 public Instant getCreatedAt(){return createdAt;}
}
