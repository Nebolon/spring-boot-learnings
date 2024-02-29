package com.nicolaihoffmann.springbootlearnings.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class XxxTokenAuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    Authentifizierungsmanager authentifizierungsmanager;

    /*@Autowired
    RestTemplate restTemplate;*/

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        if (authentifizierungsmanager.tokenIsValid(token)
        ) {
            //Token ist gültig...
        } else {
            // Token ist ungültig oder fehlt, können Sie entsprechende Maßnahmen ergreifen
            response.sendError(HttpStatus.FORBIDDEN.value(), "Not Authentificated");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(HEADER_AUTHORIZATION);
        return header;
        /*if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;*/
    }

}