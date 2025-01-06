package com.ptit.graduation.entity.product;

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
@Document(collection = "categories")
public class CategoryMongo extends BaseMongoEntity {
  @Field(name = "name")
  private String name;

  @Field(name = "type")
  private String type;

  @Field(name = "is_active")
  private boolean isActive = true;
}
