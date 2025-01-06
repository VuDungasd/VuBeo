package com.ptit.graduation.dto.response.user;

import com.ptit.graduation.entity.user.UserMongo;
import com.ptit.graduation.utils.DateUtils;

import lombok.*;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UserResponse {
    
    private int status;
    private String message;
    private UserMongo data;
    private String timestamp;
    
    public static UserResponse of(int status, String message, UserMongo data) {
        return of(status, message, data, DateUtils.getCurrentDateString());
    }

    public static UserResponse ofSuccess(String message, UserMongo data) {
        return of(200, message, data, DateUtils.getCurrentDateString());
    }

    public static UserResponse ofSuccess(String message) {
        return of(200, message, null, DateUtils.getCurrentDateString());
    }

    public static UserResponse ofCreated(String message, UserMongo data) {
        return of(201, message, data, DateUtils.getCurrentDateString());
    }

}
