package com.rootls.service;

import com.rootls.domain.User;

public interface UserService {
  public void add(User user);
  public User get(String username);
  public User updateUser(User user);
  public User getUser(String id);
  public User saveUser(User user);
}