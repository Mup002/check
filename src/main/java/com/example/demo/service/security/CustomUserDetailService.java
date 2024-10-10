package com.example.demo.service.security;

import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

import java.util.stream.Collectors;

public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUserDetail userDetail = (CustomUserDetail) getUserDetail(username);
        if(ObjectUtils.isEmpty(userDetail)){
            throw new ResourceNotFoundException("User not found with username : " + username, String.valueOf(HttpStatus.NOT_FOUND.value()));
        }
        return userDetail;
    }
    public UserDetails getUserDetail(String username){
        User user = userRepository.findUserByUsername(username);
        if(ObjectUtils.isEmpty(user)){
            throw new ResourceNotFoundException("User not found with username : " + username, String.valueOf(HttpStatus.NOT_FOUND.value()));
        }
        return new CustomUserDetail(user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList()));
    }
}
