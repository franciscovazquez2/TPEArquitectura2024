package org.example.microserv_gateway.security;

import org.example.microserv_gateway.client.UserClient;
import org.example.microserv_gateway.dto.user.AuthorityDto;
import org.example.microserv_gateway.dto.user.UserTokenDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserClient userClient;

    public DomainUserDetailsService( UserClient userClient ) {
        this.userClient = userClient;
    }

    @Override
    public UserDetails loadUserByUsername(final String username ) {
        log.debug("Authenticating {}", username);

        return userClient
                .findOneWithAuthoritiesByUsernameIgnoreCase( username.toLowerCase() )
                .map( this::createSpringSecurityUser )
                .orElseThrow( () -> new UsernameNotFoundException( "El usuario " + username + " no existe" ) );
    }

    private UserDetails createSpringSecurityUser( UserTokenDto user ) {
        List<GrantedAuthority> grantedAuthorities = user
                .getRoles()
                .stream()
                .map( AuthorityDto::getName )
                .map( SimpleGrantedAuthority::new )
                .collect( Collectors.toList() );
        return new org.springframework.security.core.userdetails.User( user.getUser(), user.getPassword(), grantedAuthorities );
    }

}
