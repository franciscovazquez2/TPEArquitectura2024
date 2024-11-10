package org.example.microservbilling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroservBillingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservBillingApplication.class, args);
    }

}
