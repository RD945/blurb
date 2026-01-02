package com.had0uken.blurb.service;


import com.had0uken.blurb.payload.requests.AuthRequest;
import com.had0uken.blurb.payload.requests.RegisterRequest;
import com.had0uken.blurb.payload.responses.Response;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface AuthService {
    Response register(RegisterRequest request);
    Response authenticate(AuthRequest request);
    PasswordEncoder getEncoder();


}
