package com.controller;

import com.dto.LoginRequest;
import com.dto.RegisterRequest;
import com.service.AuthService;
import com.service.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * Authentication controller.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Sign up.
     *
     * @param registerRequest user's parameters.
     * @return status of request.
     */
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody final RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Login.
     *
     * @param loginRequest login parameters for user.
     * @return authentication response.
     */
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody final LoginRequest loginRequest) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        return authService.login(loginRequest);
    }
}
