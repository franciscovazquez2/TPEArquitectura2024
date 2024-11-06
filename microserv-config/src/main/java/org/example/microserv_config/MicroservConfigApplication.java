package org.example.microserv_config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class MicroservConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservConfigApplication.class, args);
	}

}
