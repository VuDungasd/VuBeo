package com.ptit.graduation.service.user;

import com.ptit.graduation.entity.user.UserMongo;
import com.ptit.graduation.service.base.BaseService;


public interface UserService extends BaseService<UserMongo> {

  UserMongo login(String username, String password);

  UserMongo findByUsername(String username);

  UserMongo findByEmail(String email);

  String addUserIp();
}
