package com.smartrental.dto;
import com.smartrental.model.Role;
public record AuthResponse(String token,Long id,String name,String email,Role role){}
