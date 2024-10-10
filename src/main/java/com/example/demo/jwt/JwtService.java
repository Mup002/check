package com.example.demo.jwt;

import com.example.demo.service.security.CustomUserDetail;
import io.jsonwebtoken.Claims;
import java.security.Key;

public interface JwtService {
    Claims extractClaims(String token);
    Key getKey();
    boolean invalidToken(String token);
    String generateToken(CustomUserDetail customUserDetails);
}
