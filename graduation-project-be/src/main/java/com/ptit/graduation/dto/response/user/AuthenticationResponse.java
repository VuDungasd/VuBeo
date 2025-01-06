package com.ptit.graduation.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class AuthenticationResponse {
  private String token;

  public AuthenticationResponse(String token) {
    this.token = token;
  }

}
