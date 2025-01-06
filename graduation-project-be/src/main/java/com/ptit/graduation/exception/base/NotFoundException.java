package com.ptit.graduation.exception.base;


import static com.ptit.graduation.exception.base.StatusConstants.NOT_FOUND;

public class NotFoundException extends BaseException {
  public NotFoundException(String id, String objectName) {
    setCode("com.ptit.graduation.exception.base.NotFoundException");
    setStatus(NOT_FOUND);
    addParam("id", id);
    addParam("objectName", objectName);
  }

  public NotFoundException() {
    setCode("com.ptit.graduation.exception.base.NotFoundException");
    setStatus(NOT_FOUND);
  }
}
