package com.spring.practice.springSecurity.DTO;


import com.spring.practice.springSecurity.enums.Permission;
import com.spring.practice.springSecurity.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {

    private String email;
    private String name;
    private String password;
    private String plan;
    private Set<Role> roles;
    private Set<Permission> permissions;
}
