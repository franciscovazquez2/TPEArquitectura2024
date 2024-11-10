package org.example.microserv_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class MicroservEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservEurekaApplication.class, args);
	}

}
