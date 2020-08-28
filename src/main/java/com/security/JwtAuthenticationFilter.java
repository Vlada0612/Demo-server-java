package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyStoreException;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.util.StringUtils.hasText;

/**
 * JWT authentication filter.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Do internal filter.
     *
     * @param request http request.
     * @param response http servlet response.
     * @param filterChain filter chain.
     * @throws ServletException servlet exception.
     * @throws IOException io exception.
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final String jwt = getJwtFromRequest(request);
        try {
            if (hasText(jwt) && jwtProvider.validateToken(jwt)) {
                setAuthentication(request, jwt);
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(final HttpServletRequest request, final String jwt) throws KeyStoreException {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtProvider.getUsernameFromJWT(jwt));
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        getContext().setAuthentication(authentication);
    }

    private String getJwtFromRequest(final HttpServletRequest request) {
        final String bearerToken = request.getHeader("Authorization");
        if (hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}
