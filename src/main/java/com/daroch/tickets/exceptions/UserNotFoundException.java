package com.daroch.tickets.exceptions;

public class UserNotFoundException extends RuntimeException {

  // No-arg constructor
  public UserNotFoundException() {
    super();
  }

  // Constructor with message
  public UserNotFoundException(String message) {
    super(message);
  }

  // Constructor with message and cause
  public UserNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  // Constructor with cause only
  public UserNotFoundException(Throwable cause) {
    super(cause);
  }

  // Full constructor
  public UserNotFoundException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
