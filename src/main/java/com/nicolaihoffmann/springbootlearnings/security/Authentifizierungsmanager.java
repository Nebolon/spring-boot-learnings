package com.nicolaihoffmann.springbootlearnings.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class Authentifizierungsmanager {

    public static final String HEADER_AUTHORIZATION = "Authorization";

    Logger logger = LoggerFactory.getLogger(Authentifizierungsmanager.class);

    @Cacheable("date4you")
    public boolean tokenIsValid(String token) {
        // Implementieren Sie die Logik zur Überprüfung der Gültigkeit des Tokens
        System.out.println("Beispiel für caching {} "+ token);

        RestTemplateBuilder builder      = new RestTemplateBuilder();
        RestTemplate        restTemplate = builder.defaultHeader(HEADER_AUTHORIZATION, token).build();

        List<PermissionDomainDTO> hi = restTemplate.getForObject(
                "http://localhost:8088/XXX/v2/permissions", List.class);
        return hi.size() > 0;
    }


}
