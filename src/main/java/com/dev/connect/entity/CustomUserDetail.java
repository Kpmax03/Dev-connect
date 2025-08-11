package com.dev.connect.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString@Builder
public class CustomUserDetail implements UserDetails{
    @Autowired
    private User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         List<GrantedAuthority> auth=user.getRole().stream().map(oneRole-> {return new SimpleGrantedAuthority(oneRole.getName());}).collect(Collectors.toList());
    return auth;
   }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        return true;
    }
}
