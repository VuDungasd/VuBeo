package com.ptit.graduation.entity.base;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseMongoEntity {
  @Id
  @JsonFormat
  private String id;

  @CreatedDate
  @Field(name = "created_at")
  private Long createdAt;

  @LastModifiedDate
  @Field(name = "updated_at")
  private Long updatedAt;

  @CreatedBy
  @Field(name = "created_by")
  private String createdBy;

  @LastModifiedBy
  @Field(name = "updated_by")
  private String updatedBy;

}

