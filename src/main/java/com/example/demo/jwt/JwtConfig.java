package com.example.demo.jwt;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;

@Data
public class JwtConfig {

    @Value("${jwt.url:/api/account/login}")
    private String url;

    @Value("${jwt.header:Authorization}")
    private String header;

    @Value("${jwt.prefix:Bearer}")
    private String prefix;

    @Value("${jwt.expiration:#{60*60}}")
    private int expiration;

    @Value("${jwt.secret:fXk2x7qRbnuaUZpc5uijfGvFJYf9bWlpHbaX8it1yC/elK2bRF2HsawRJp6bAJAC}")
    private String secret;
}