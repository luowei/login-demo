package com.rootls.repository;

import com.mongodb.WriteConcern;
import com.rootls.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {
  
  protected final Logger log = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private MongoTemplate mongoTemplate;
  
  public void add(User user) {
    log.info("Adding new user");
    try {
      mongoTemplate.setWriteConcern(WriteConcern.REPLICAS_SAFE);
      mongoTemplate.insert(user, "usercollection");
     }
     catch (Exception e) {
      log.error("An error has occurred while trying to add new user", e);
     }
  }

  public User get(String userName) {
    try {
      Query query = new Query(where("username").is(userName));
      User user = mongoTemplate.findOne(query, User.class, "usercollection");
      return user;
    }
    catch(Exception e) {
      log.error("getting user by userName failed", e);
      return new User();
    }
  }
  
  public User addUser(User user) {
    mongoTemplate.setWriteConcern(WriteConcern.REPLICAS_SAFE);
    mongoTemplate.insert(user, "usercollection");
    return getUser(user.getId());
  }
  
  public User updateUser(User user) {
    mongoTemplate.setWriteConcern(WriteConcern.REPLICAS_SAFE);
    mongoTemplate.save(user, "usercollection"); 
    return getUser(user.getId());
  }
  
  public User getUser(String id) {
    return mongoTemplate.findById(id, User.class, "usercollection");
  }
  
  public void setMongoTemplate(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }
  
  public void saveUser(User user) {
    mongoTemplate.setWriteConcern(WriteConcern.REPLICAS_SAFE);
    mongoTemplate.save(user);
  }

  public User save(User user) {
    mongoTemplate.setWriteConcern(WriteConcern.REPLICAS_SAFE);
    mongoTemplate.save(user);
    return getUser(user.getId());
  }
}
