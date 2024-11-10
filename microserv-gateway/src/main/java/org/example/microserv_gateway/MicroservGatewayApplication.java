package org.example.microserv_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroservGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservGatewayApplication.class, args);
	}

}
