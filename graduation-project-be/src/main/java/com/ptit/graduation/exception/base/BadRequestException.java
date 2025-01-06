package com.ptit.graduation.exception.base;


import static com.ptit.graduation.exception.base.StatusConstants.BAD_REQUEST;

public class BadRequestException extends BaseException {
  public BadRequestException() {
    setCode("com.ptit.graduation.exception.base.BadRequestException");
    setStatus(BAD_REQUEST);
  }

  public BadRequestException(String message) {
    setCode(message);
    setStatus(BAD_REQUEST);
  }

}
