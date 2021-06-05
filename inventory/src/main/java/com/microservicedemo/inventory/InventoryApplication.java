package com.microservicedemo.inventory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;

/**
 * @author sravantatikonda
 */
@SpringBootApplication
@Slf4j
public class InventoryApplication {

	private static final String SERVER_PORT = "server.port";

	public static void main(String[] args) {
		log.info("");
		log.info(
				"***********************************************************************************");
		log.info(
				"** I N V E N T O R Y  S E R V I C E **");
		log.info(
				"***********************************************************************************");
		log.info("");

		Environment env = new SpringApplicationBuilder(InventoryApplication.class).run(args)
				.getEnvironment();
		String protocol = "http";

		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}

		log.info(
				"\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t"
						+ "Local HTTP : \t\thttp://localhost:9006/swagger-ui.html\n\t"
						+ "\n----------------------------------------------------------\n\t",
				env.getProperty("spring.application.name"), protocol, env.getProperty(SERVER_PORT));
	}

}
