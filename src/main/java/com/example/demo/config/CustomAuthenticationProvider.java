package com.example.demo.config;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    public UserRepository userRepository;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();
        User user = userRepository.findUserByUsername(username);
        if(ObjectUtils.isEmpty(user)){
            throw new ResourceNotFoundException("User not found with username : " + username, String.valueOf(HttpStatus.NOT_FOUND.value()));
        }
        final List<GrantedAuthority> authorities = getAuthorites(user.getRoles());
        final Authentication auth = new UsernamePasswordAuthenticationToken(username,password,authorities);
        return auth;
    }

    public List<GrantedAuthority> getAuthorites(List<Role> roles){
        List<GrantedAuthority> rs = new ArrayList<>();
        Set<String> permissions = new HashSet<>();

        if(!ObjectUtils.isEmpty(roles)){
            roles.forEach(r -> permissions.add(r.getRoleName()));
        }

        permissions.forEach(p -> rs.add(new SimpleGrantedAuthority(p)));
        return rs;
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
