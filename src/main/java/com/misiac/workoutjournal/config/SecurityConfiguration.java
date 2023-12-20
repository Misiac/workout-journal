package com.misiac.workoutjournal.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        // DISABLE CSRF
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // PROTECT ENDPOINTS
        httpSecurity.authorizeHttpRequests(request -> request
                        .requestMatchers("/api/**")
                        .authenticated())
                .oauth2ResourceServer(v -> v.jwt(jwtConfigurer -> {
                }));


//         ADD CORS FILTERS
        httpSecurity.cors(httpSecurityCorsConfigurer -> {
        });

//        ADD content negotiation strategy
        httpSecurity.setSharedObject(ContentNegotiationStrategy.class,
                new HeaderContentNegotiationStrategy());

        // force a non-empty response body for 4-1's to make it user-friendly
        Okta.configureResourceServer401ResponseBody(httpSecurity);


        return httpSecurity.build();
    }

}
