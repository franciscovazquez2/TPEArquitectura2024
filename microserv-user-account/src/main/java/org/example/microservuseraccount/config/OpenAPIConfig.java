package org.example.microservuseraccount.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@OpenAPIDefinition
@Configuration
public class OpenAPIConfig {


    @Bean
    @Primary
    public OpenAPI userOpenAPI() {
        return new OpenAPI()
                .servers(List.of(new Server().url("http://localhost:8080/api/user")))
                .info(new Info().title("User Service API").version("1.0.0"));
    }

    @Bean
    public OpenAPI accountOpenAPI() {
        return new OpenAPI()
                .servers(List.of(new Server().url("http://localhost:8080/api/account")))
                .info(new Info().title("Account Service API").version("1.0.0"));
    }
}
