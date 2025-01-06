package com.ptit.graduation.entity.base;

import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BaseEntityBeforeConvertCallback implements BeforeConvertCallback<BaseMongoEntity> {
  @Override
  public BaseMongoEntity onBeforeConvert(BaseMongoEntity entity, String collection) {
    if (entity.getId() == null) {
      entity.setId(UUID.randomUUID().toString());
    }
    return entity;
  }
}
