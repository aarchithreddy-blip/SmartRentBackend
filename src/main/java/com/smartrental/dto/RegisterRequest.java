package com.smartrental.dto;
import com.smartrental.model.Role;
import jakarta.validation.constraints.*;
public record RegisterRequest(@NotBlank @Size(min=2,max=100) String name,@NotBlank @Email String email,@NotBlank @Size(min=8,max=100) String password,@Size(max=30) String phone,Role role){}
