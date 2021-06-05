package com.microservicedemo.userssearchservice.dto;

public class ErrorResponse {

  private int errorCode;

  private String errorMessage;

  public ErrorResponse() {
    super();
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}