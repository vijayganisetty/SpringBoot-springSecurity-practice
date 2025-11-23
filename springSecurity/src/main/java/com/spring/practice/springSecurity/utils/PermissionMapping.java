package com.spring.practice.springSecurity.utils;

import com.spring.practice.springSecurity.enums.Permission;
import com.spring.practice.springSecurity.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.spring.practice.springSecurity.enums.Permission.*;
import static com.spring.practice.springSecurity.enums.Role.*;

public class PermissionMapping {

    private static Map<Role, Set<Permission>> map = Map.of(
            USER, Set.of(USER_VIEW, BOOK_VIEW),
            CREATOR, Set.of(BOOK_CREATE, BOOK_UPDATE, USER_UPDATE),
            ADMIN, Set.of(BOOK_CREATE, BOOK_DELETE, BOOK_UPDATE, USER_CREATE, USER_DELETE, USER_UPDATE)
    );

    public static Set<SimpleGrantedAuthority> getAuthories(Role role){
        return map.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }

}
