package com.rootls.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rootls.domain.User;
import com.rootls.repository.UserRepository;

@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService, UserService {
  @Autowired
  private UserRepository userRepository;
  protected final Logger log = LoggerFactory.getLogger(getClass());
  
  @Transactional(readOnly = false)
  public void add(User user) {
    userRepository.add(user);
  }
  
  public User get(String username) {
    return userRepository.get(username);
  }
 
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = false)
  public User updateUser(User user) {
    return userRepository.updateUser(user);
  }
  
  public User getUser(String id) {
    return userRepository.getUser(id);
  }
  
  @Transactional(readOnly = false)
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String userName)
      throws UsernameNotFoundException {
    return userRepository.get(userName);
  }
}