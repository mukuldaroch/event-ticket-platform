package com.daroch.tickets.exceptions;

public class QrCodeGenerationException extends RuntimeException {

  // No-arg constructor
  public QrCodeGenerationException() {
    super();
  }

  // Constructor with message
  public QrCodeGenerationException(String message) {
    super(message);
  }

  // Constructor with message and cause
  public QrCodeGenerationException(String message, Throwable cause) {
    super(message, cause);
  }

  // Constructor with cause only
  public QrCodeGenerationException(Throwable cause) {
    super(cause);
  }

  // Full constructor
  public QrCodeGenerationException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
