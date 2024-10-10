package com.example.demo.config.filter;

import com.example.demo.dto.LoginRequest;
import com.example.demo.jwt.JwtConfig;
import com.example.demo.jwt.JwtService;
import com.example.demo.service.security.CustomUserDetail;
import com.example.demo.utils.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collections;

public class JwtUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager manager, JwtService jwtService, JwtConfig jwtConfig){
        super(new AntPathRequestMatcher(jwtConfig.getUrl(),"POST"));
        setAuthenticationManager(manager);
        this.jwtService = jwtService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword(),
                        Collections.emptyList())
        );

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserDetail userDetail = (CustomUserDetail) authResult.getDetails();
        String accessToken = jwtService.generateToken(userDetail);
        String json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(accessToken);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        BaseResponse baseResponse = BaseResponse.builder()
                .message(failed.getLocalizedMessage())
                .code(String.valueOf(HttpStatus.UNAUTHORIZED)).build();
        String json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(baseResponse);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json);
    }
}
