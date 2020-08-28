package com.service;

import com.dto.LoginRequest;
import com.dto.RegisterRequest;
import com.model.User;
import com.repository.UserRepository;
import com.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

/**
 * Authentication service.
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    /**
     * Sign up.
     *
     * @param registerRequest register request.
     */
    public void signup(final RegisterRequest registerRequest) {
        final User user = setUserParametersFromRegistrationInfo(registerRequest);
        userRepository.save(user);
    }

    /**
     * Login.
     *
     * @param loginRequest login request.
     * @return authentication response.
     * @throws UnrecoverableKeyException unrecoverable key exception.
     * @throws NoSuchAlgorithmException  no such algorithm exception.
     * @throws KeyStoreException         key store exception.
     */
    public AuthenticationResponse login(final LoginRequest loginRequest) throws UnrecoverableKeyException,
                                                                                NoSuchAlgorithmException, KeyStoreException {
        final Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        getContext().setAuthentication(authenticate);
        return new AuthenticationResponse(jwtProvider.generateToken(authenticate), loginRequest.getUsername());
    }

    private User setUserParametersFromRegistrationInfo(final RegisterRequest registerRequest) {
        final User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        return user;
    }

    private String encodePassword(final String password) {
        return passwordEncoder.encode(password);
    }

}
