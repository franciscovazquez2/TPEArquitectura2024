package org.example.microservtravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroservTravelApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservTravelApplication.class, args);
    }

}
