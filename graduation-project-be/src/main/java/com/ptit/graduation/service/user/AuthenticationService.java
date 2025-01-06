package com.ptit.graduation.service.user;

import com.ptit.graduation.dto.request.user.AuthenticationRequest;
import com.ptit.graduation.dto.response.user.AuthenticationResponse;
import com.ptit.graduation.repository.user.UserMongoRepository;
import com.ptit.graduation.service.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthenticationService {
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
  private final UserMongoRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtils jwtUtils;

  public AuthenticationService(UserMongoRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtils = jwtUtils;
  }

//  public AuthenticationResponse authenticate(AuthenticationRequest request) {
//    logger.info("Authenticating user with username: {}", request.getUsername());
//    var user = userRepository.findByUsername(request.getUsername());
//    //          .orElseThrow(() -> new RuntimeException("User not found"));
//
//    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//      throw new RuntimeException("Invalid credentials");
//    }
//
//    String token = jwtUtils.generateToken(user.getUsername());
//    return new AuthenticationResponse(token);
//  }
public AuthenticationResponse authenticate(AuthenticationRequest request) {
  logger.info("Authenticating user with username: {}", request.getUsername());

  // Tìm user trong cơ sở dữ liệu
  var user = userRepository.findByUsername(request.getUsername());
  if (user == null) {
    logger.error("User with username '{}' not found", request.getUsername());
    throw new RuntimeException("User not found");
  }

  logger.debug("User found: {}", user);

  // Kiểm tra mật khẩu
  if (!passwordEncoder.matches(request.getPassword(), user.getUsername())) {
    logger.warn("Invalid credentials for username: {}", request.getUsername());
    throw new RuntimeException("Invalid credentials");
  }

  // Sinh token
  String token = jwtUtils.generateToken(user.getUsername());
  logger.info("Generated token for user '{}': {}", user.getUsername(), token);

  return new AuthenticationResponse(token);
}

}
