package com.ptit.graduation.dto.response.user;

import java.util.List;

import com.ptit.graduation.entity.user.UserMongo;
import com.ptit.graduation.utils.DateUtils;

import lombok.*;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UserListResponse {

    private int status;
    private String message;
    private List<UserMongo> data;
    private String timestamp;
    private int total;

    public static UserListResponse of(int status, String message, List<UserMongo> data, int total) {
        return of(status, message, data, DateUtils.getCurrentDateString(), total);
    }

    public static UserListResponse ofSuccess(String message, List<UserMongo> data, int total) {
        return of(200, message, data, DateUtils.getCurrentDateString(), total);
    }

    public static UserListResponse ofSuccess(String message, int total) {
        return of(200, message, null, DateUtils.getCurrentDateString(), total);
    }
    
}
