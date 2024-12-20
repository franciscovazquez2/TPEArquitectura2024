package org.example.microserv_gateway.client;

import org.example.microserv_gateway.dto.user.UserTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "microserv-user-account")
public interface UserClient {

    @GetMapping("/api/user/auth/{username}")
    Optional<UserTokenDto> findOneWithAuthoritiesByUsernameIgnoreCase(@PathVariable String username );
}
