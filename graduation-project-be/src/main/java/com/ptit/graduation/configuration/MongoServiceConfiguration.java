package com.ptit.graduation.configuration;

import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = {"com.ptit.graduation.repository"})
public class MongoServiceConfiguration {

  @Value("${spring.data.mongodb.host}")
  private String host;

  @Value("${spring.data.mongodb.port}")
  private int port;

  @Value("${spring.data.mongodb.database}")
  private String database;

  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(MongoClients.create("mongodb://" + host + ":" + port), database);
  }
}

