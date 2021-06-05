package com.microservicedemo.usersservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author sravantatikonda
 */
@SpringBootApplication
@Slf4j
@EnableTransactionManagement
public class UsersServiceApplication {

  private static final String SERVER_PORT = "server.port";

  public static void main(String[] args) {
    log.info("");
    log.info(
        "***********************************************************************************");
    log.info(
        "** U S E R S  S E R V I C E **");
    log.info(
        "***********************************************************************************");
    log.info("");

    Environment env = new SpringApplicationBuilder(UsersServiceApplication.class).run(args)
        .getEnvironment();
    String protocol = "http";

    if (env.getProperty("server.ssl.key-store") != null) {
      protocol = "https";
    }

    log.info(
        "\n----------------------------------------------------------\n\t"
            + "Application '{}' is running! Access URLs:\n\t"
            + "Local HTTP : \t\thttp://localhost:9001/swagger-ui.html\n\t"
            + "\n----------------------------------------------------------\n\t",
        env.getProperty("spring.application.name"), protocol, env.getProperty(SERVER_PORT));
  }
}
