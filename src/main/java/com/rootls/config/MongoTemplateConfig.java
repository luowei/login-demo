package com.rootls.config;

import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoTemplateConfig {

  @Bean
  public Mongo mongo() throws Exception {
    
    List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();    
    serverAddresses.add( new ServerAddress( "localhost", 27017 ) );
    MongoOptions options = new MongoOptions();
    options.autoConnectRetry = true; 
    options.connectTimeout = 6000;
    options.socketTimeout = 120000;
    options.maxAutoConnectRetryTime = 8000;
    options.connectionsPerHost = 50;
    options.threadsAllowedToBlockForConnectionMultiplier = 1000;
    options.socketKeepAlive = true;

    Mongo mongo  = new Mongo( serverAddresses, options );
    mongo.setReadPreference(ReadPreference.SECONDARY);
    return mongo;
  }
  
  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
    return new MongoTemplate(mongo(), "schema");
  }
  
  @Bean
  public MongoOperations mongoOperations() throws Exception {
    return (MongoOperations)new MongoTemplate(mongo(), "schema");
  }
}  