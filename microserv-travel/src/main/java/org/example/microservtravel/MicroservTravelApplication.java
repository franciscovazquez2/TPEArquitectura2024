package org.example.microservtravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroservTravelApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservTravelApplication.class, args);
    }

}
