package com.proyectotitulo.springbootproyectotitulo.configuracion;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.csrf().disable();


        http.authorizeRequests(configurer ->
                        configurer
                                .antMatchers("/api/libroes/confidencial/**",
                                                        "/api/resenas/confidencial/**",
                                                        "/api/mensajes/confidencial/**",
                                                        "/api/admin/confidencial/**"
                                )
                                .authenticated())
                .oauth2ResourceServer()
                .jwt();


        http.cors();


        http.setSharedObject(ContentNegotiationStrategy.class,
                new HeaderContentNegotiationStrategy());


        Okta.configureResourceServer401ResponseBody(http);

        return http.build();

    }
}
