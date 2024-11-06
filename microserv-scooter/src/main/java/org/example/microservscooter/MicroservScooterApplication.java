package org.example.microservscooter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroservScooterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservScooterApplication.class, args);
    }

}
