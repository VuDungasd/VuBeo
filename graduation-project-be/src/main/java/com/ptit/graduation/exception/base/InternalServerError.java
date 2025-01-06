package com.ptit.graduation.exception.base;


import static com.ptit.graduation.exception.base.StatusConstants.INTERNAL_SERVER_ERROR;

public class InternalServerError extends BaseException {
  public InternalServerError() {
    setCode("com.ptit.graduation.exception.base.InternalServerError");
    setStatus(INTERNAL_SERVER_ERROR);
  }
}
