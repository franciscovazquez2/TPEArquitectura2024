package org.example.microserv_gateway.config;

import org.example.microserv_gateway.security.AuthorityConstant;
import org.example.microserv_gateway.security.jwt.JwtFilter;
import org.example.microserv_gateway.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    public SecurityConfig( TokenProvider tokenProvider ) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http ) throws Exception {
        http
                .csrf( AbstractHttpConfigurer::disable );
        http
                .sessionManagement( s -> s.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) );
        http
                .securityMatcher("/api/**" )
                .authorizeHttpRequests( authz -> authz
                        .requestMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/user").permitAll()
                        .requestMatchers( HttpMethod.GET,"/api/user").hasAuthority( AuthorityConstant._USER )//el orden va de más específica a menos específica
                        .requestMatchers( "/api/carreras/**").hasAuthority( AuthorityConstant._ADMIN) // ésta es menos específica que la de arriba
                        //.requestMatchers("/api/estudiantes/**").hasAuthority( AuthorityConstant._ALUMNO )
                        //.requestMatchers( "/api/inscripciones/**").hasAuthority( AuthorityConstant._ADMIN )
                        .anyRequest().authenticated()
                )
                .httpBasic( Customizer.withDefaults() )
                .addFilterBefore( new JwtFilter( this.tokenProvider ), UsernamePasswordAuthenticationFilter.class );
        return http.build();
    }

}