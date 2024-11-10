package src.main.java.org.example.arquitecturawebtpe2024;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Electric Scooter API",
                description = "API for managing scooters, users and travels",
                version = "1.0.0"
        ),
        servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8080"
                )
        }
)

public class SwaggerConfig {
}
