package com.ptit.graduation.entity.user;

import com.ptit.graduation.dto.response.user.Role;
import com.ptit.graduation.entity.base.BaseMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class UserMongo extends BaseMongoEntity {
  @Field(name = "username")
  private String username;

  @Field(name = "password")
  private String password;

  @Field(name = "email")
  private String email;

  @Field(name = "is_staff")
  private boolean isStaff = false;

  @Field(name = "is_superuser")
  private boolean isSuperuser = false;

  @Field(name = "full_name")
  private String fullName;

  @Field(name = "last_name")
  private String lastName;

  @Field(name = "is_active")
  private boolean isActive = true;

  private Role role; // Thêm trường role
}
