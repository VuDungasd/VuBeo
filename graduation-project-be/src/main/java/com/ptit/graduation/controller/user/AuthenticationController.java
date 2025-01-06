package com.ptit.graduation.controller.user;

import com.ptit.graduation.dto.request.user.AuthenticationRequest;
import com.ptit.graduation.entity.user.UserMongo;
import com.ptit.graduation.service.security.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {

  // Lưu trữ token bị vô hiệu hóa (có thể thay thế bằng Redis hoặc Database)
  private final Map<String, String> revokedTokens = new HashMap<>();

  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;

  public AuthenticationController(JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
    this.jwtUtils = jwtUtils;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationRequest user) {
    try {
      // Xác thực người dùng với username và password
      Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
      );

      // Lấy thông tin user từ context
//      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//      User authenticatedUser = (User) userDetails;
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      // Lấy id người dùng (giả sử id có trong UserDetails hoặc bạn có thể truy cập qua User)
//      String userId = ((User) userDetails).getId(); // Nếu UserDetails là instance của User
//      System.out.println(userId);
      // Tạo access token và refresh token
      String accessToken = jwtUtils.generateToken(userDetails.getUsername());
      String refreshToken = jwtUtils.generateRefreshToken(userDetails.getUsername());

      // Trả về các token
      Map<String, String> tokens = new HashMap<>();
      tokens.put("access_token", accessToken);
      tokens.put("refresh_token", refreshToken);

      return ResponseEntity.ok(tokens);
    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "Invalid username or password"));
    }
  }

  @PostMapping("/refresh")
  public ResponseEntity<Map<String, String>> refresh(@RequestBody Map<String, String> tokenRequest) {
    String accessToken = tokenRequest.get("access_token");
    String refreshToken = tokenRequest.get("refresh_token");

    // Validate both tokens are present
    if (accessToken == null || refreshToken == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("error", "Both access and refresh tokens are required"));
    }

    try {
      // Remove "Bearer " if present
      accessToken = accessToken.replace("Bearer ", "");
      refreshToken = refreshToken.replace("Bearer ", "");

      // Extract username from refresh token
      String username = jwtUtils.extractUsername(refreshToken);

      // Additional validation steps
      // 1. Validate refresh token
      if (!jwtUtils.isRefreshTokenValid(refreshToken)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body(Map.of("error", "Invalid or expired refresh token"));
      }

      // 2. Validate access token (optional extra security check)
      // Note: Access token might be expired, so use a lenient validation
      if (!jwtUtils.isAccessTokenValidForRefresh(accessToken, username)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body(Map.of("error", "Invalid access token"));
      }

      // Generate new tokens
      String newAccessToken = jwtUtils.generateToken(username);
      String newRefreshToken = jwtUtils.generateRefreshToken(username);

      // Prepare response
      Map<String, String> response = new HashMap<>();
      response.put("access_token", newAccessToken);
      response.put("refresh_token", newRefreshToken);

      return ResponseEntity.ok(response);

    } catch (ExpiredJwtException e) {
      // Handle token expiration
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "Token has expired"));
    } catch (JwtException e) {
      // Handle other JWT-related exceptions
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("error", "Invalid token"));
    } catch (Exception e) {
      // Handle unexpected errors
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("error", "An unexpected error occurred"));
    }
  }

  // Endpoint logout
  @PostMapping("/logout")
  public ResponseEntity<Map<String, String>> logout(@RequestHeader("Authorization") String token) {
    try {
      // Loại bỏ "Bearer " khỏi token
      String accessToken = token.replace("Bearer ", "");

      // Vô hiệu hóa token bằng cách lưu vào danh sách đen
      revokedTokens.put(accessToken, "revoked");

      // Xóa thông tin xác thực trong SecurityContextHolder
      SecurityContextHolder.clearContext();

      // Trả về phản hồi thành công
      return ResponseEntity.ok(Map.of("message", "Logout successful"));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("error", "An error occurred during logout"));
    }
  }

}
