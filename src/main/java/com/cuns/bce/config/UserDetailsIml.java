package com.cuns.bce.config;

import com.cuns.bce.entities.Authority;
import com.cuns.bce.entities.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
public class UserDetailsIml implements UserDetails {
    private  String username;
    private String password;
    private boolean enabled;
    private final List<SimpleGrantedAuthority> authorities;

    public UserDetailsIml(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enabled = user.getEnabled();
//        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        this.authorities = getAuthorities(user.getAuthorities());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username; // this is the only difference between this class and User.java
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    private List<SimpleGrantedAuthority> getAuthorities(Set<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .toList();
    }
}
