package com.example.demo.service;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.utils.BaseResponse;

public interface UserService {
    String register(RegisterRequest request);
}
