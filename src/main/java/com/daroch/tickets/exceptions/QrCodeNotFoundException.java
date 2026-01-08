package com.daroch.tickets.exceptions;

public class QrCodeNotFoundException extends RuntimeException {

  // No-arg constructor
  public QrCodeNotFoundException() {
    super();
  }

  // Constructor with message
  public QrCodeNotFoundException(String message) {
    super(message);
  }

  // Constructor with message and cause
  public QrCodeNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  // Constructor with cause only
  public QrCodeNotFoundException(Throwable cause) {
    super(cause);
  }

  // Full constructor
  public QrCodeNotFoundException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
