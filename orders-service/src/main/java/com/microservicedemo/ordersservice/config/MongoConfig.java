package com.microservicedemo.ordersservice.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/*@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

  @Override
  protected String getDatabaseName() {
    return "test";
  }

  @Override
  public MongoClient mongoClient() {
    ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/test");
    MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .build();

    return MongoClients.create(mongoClientSettings);
  }

  @Override
  public Collection getMappingBasePackages() {
    return Collections.singleton("com.microservices");
  }
}*/


/**
 * @author sravantatikonda
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.microservicedemo.ordersservice.repository")
public class MongoConfig {

  @Bean
  public MongoClient mongo() {
    ConnectionString connectionString = new ConnectionString("mongodb://192.168.1.170:27017/test");
    MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .build();

    return MongoClients.create(mongoClientSettings);
  }

  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
    return new MongoTemplate(mongo(), "test");
  }
}