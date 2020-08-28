package com.service;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Authentication response.
 */
@Data
@AllArgsConstructor
public class AuthenticationResponse {

    private String authenticationToken;
    private String username;
}
