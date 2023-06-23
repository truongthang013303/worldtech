package com.example.demo1.dto;

import com.example.demo1.entity.CategoryEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Getter
@Setter
public class AppUser extends User {
    private Long id;

    private Collection roles;
    private Collection privileges;

    private CategoryEntity interest;
    public AppUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Collection roles, Collection privileges, CategoryEntity interest) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.roles=roles;
        this.privileges=privileges;
        this.interest=interest;
    }

    public AppUser(Long id, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Collection roles, Collection privileges, CategoryEntity interest) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.roles=roles;
        this.privileges=privileges;
        this.interest=interest;
        this.id=id;
    }
}
