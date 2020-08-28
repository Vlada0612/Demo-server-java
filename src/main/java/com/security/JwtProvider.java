package com.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.builder;
import static io.jsonwebtoken.Jwts.parser;

/**
 * JWT provider.
 */
@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        keyStore = KeyStore.getInstance("JKS");
        final InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
        keyStore.load(resourceAsStream, "secret".toCharArray());
    }

    /**
     * Generate token.
     *
     * @param authentication authentication.
     * @return token.
     */
    public String generateToken(final Authentication authentication) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        final User principal = (User) authentication.getPrincipal();
        return builder().setSubject(principal.getUsername()).signWith(getPrivateKey()).compact();
    }

    /**
     * Validate token.
     *
     * @param jwt JWT.
     * @return true if token is validate and false in another case.
     */
    public boolean validateToken(final String jwt) throws KeyStoreException {
        parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
        return true;
    }

    /**
     * Get username from JWT.
     *
     * @param token token.
     * @return username.
     */
    public String getUsernameFromJWT(final String token) throws KeyStoreException {
        return parser().setSigningKey(getPublickey()).parseClaimsJws(token).getBody().getSubject();
    }

    private PrivateKey getPrivateKey() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
    }

    private PublicKey getPublickey() throws KeyStoreException {
        return keyStore.getCertificate("springblog").getPublicKey();
    }
}
