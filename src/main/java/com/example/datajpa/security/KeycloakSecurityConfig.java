package com.example.datajpa.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class KeycloakSecurityConfig {

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
            Collection<String> roles = realmAccess.get("roles");

            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        };
        JwtAuthenticationConverter jwtConverter= new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtConverter;
    }
    @Bean
    public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {



//        all requests must be authenticated
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers(HttpMethod.POST, "/api/v1/customers/**")
                .hasAnyRole("ADMIN", "STAFF")
                .requestMatchers(HttpMethod.GET, "/api/v1/customers/**")
                .hasAnyRole("ADMIN", "STAFF", "CUSTOMER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/customers/**")
                .hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/customers/**")
                .hasAnyRole("ADMIN", "STAFF")
                .requestMatchers(HttpMethod.POST, "/api/v1/accounts/**")
                .hasAnyRole("USER")
                .requestMatchers("/media/**").permitAll()
                .anyRequest().authenticated());


//        disable form login
        http.formLogin(form -> form.disable());
        http.csrf(token -> token.disable());

        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverterForKeycloak()))
        );

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();

    }
}
