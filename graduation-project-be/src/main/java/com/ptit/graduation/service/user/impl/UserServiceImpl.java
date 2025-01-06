package com.ptit.graduation.service.user.impl;

import com.ptit.graduation.entity.user.UserMongo;
import com.ptit.graduation.repository.user.UserMongoRepository;
import com.ptit.graduation.service.base.impl.BaseServiceImpl;
import com.ptit.graduation.service.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMongo> implements UserService {
  private final UserMongoRepository userRepository;

  @Autowired
  private HttpServletRequest request;

  public UserServiceImpl(UserMongoRepository userRepository) {
    super(userRepository);
    this.userRepository = userRepository;
  }

  @Override
  public UserMongo login(String username, String password) {
    log.info("(login) username: {}", username);

    return userRepository.login(username, password);
  }

  @Override
  public UserMongo findByUsername(String username) {
    log.info("(findByUsername) username: {}", username);

    return userRepository.findByUsername(username);
  }

  @Override
  public UserMongo findByEmail(String email) {
    log.info("(findByEmail) email: {}", email);

    return userRepository.findByEmail(email);
  }

  @Override
  public String addUserIp() {
    // TODO Auto-generated method stub
    String ip = request.getRemoteAddr();
    return ip;
  }

}
