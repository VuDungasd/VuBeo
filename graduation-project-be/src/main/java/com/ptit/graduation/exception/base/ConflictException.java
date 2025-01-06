package com.ptit.graduation.exception.base;


import static com.ptit.graduation.exception.base.StatusConstants.CONFLICT;

public class ConflictException extends BaseException {
  public ConflictException(String id, String objectName) {
    setStatus(CONFLICT);
    setCode("com.ptit.graduation.exception.base.ConflictException");
    addParam("id", id);
    addParam("objectName", objectName);
  }

  public ConflictException(){
    setStatus(CONFLICT);
    setCode("com.ptit.graduation.exception.base.ConflictException");
  }
}
