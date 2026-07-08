package com.medeil.productservice.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
public class JwtAuthenticationConverterConfig {

    @Bean
    Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {

        return jwt -> {

            List<String> roles = jwt.getClaimAsStringList("roles");

            Collection<GrantedAuthority> authorities =
                    roles.stream()
                         .map(SimpleGrantedAuthority::new)
                         .collect(Collectors.toList());

            return new JwtAuthenticationToken(jwt, authorities);
        };
    }

}