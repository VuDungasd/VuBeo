package com.ptit.graduation.controller.user;

import com.ptit.graduation.dto.response.user.UserListResponse;
import com.ptit.graduation.dto.response.user.UserResponse;
import com.ptit.graduation.entity.user.UserMongo;
import com.ptit.graduation.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;


  @GetMapping("")
  public ResponseEntity<UserListResponse> list() {
    List<UserMongo> users = userService.list();
    UserListResponse response = UserListResponse.ofSuccess("List users", users, users.size());

    return new ResponseEntity<UserListResponse>(response, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<UserResponse> create(@RequestBody UserMongo userResponse) {
    UserMongo user = userService.create(userResponse);
    UserResponse response = UserResponse.ofCreated("User created", user);

    return new ResponseEntity<UserResponse>(response, HttpStatus.CREATED);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<UserResponse> update(@RequestParam String id, @RequestBody UserMongo userResponse) {
    userResponse.setId(id);
    UserMongo user = userService.update(userResponse);
    UserResponse response = UserResponse.ofSuccess("User updated", user);

    return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<UserResponse> login(@RequestParam String username, @RequestParam String password) {
    UserMongo user = userService.login(username, password);
    UserResponse response = UserResponse.ofSuccess("Login success", user);

    return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
  }

  @GetMapping("/find-by-username")
  public ResponseEntity<UserResponse> findByUsername(@RequestParam String username) {
    UserMongo user = userService.findByUsername(username);
    UserResponse response = UserResponse.ofSuccess("Find user by username", user);

    return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
  }

  @GetMapping("/find-by-email")
  public ResponseEntity<UserResponse> findByEmail(@RequestParam String email) {
    UserMongo user = userService.findByEmail(email);
    UserResponse response = UserResponse.ofSuccess("Find user by email", user);

    return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<UserResponse> delete(@RequestParam String id) {
    userService.delete(id);
    UserResponse response = UserResponse.ofSuccess("User deleted");

    return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
  }


}
