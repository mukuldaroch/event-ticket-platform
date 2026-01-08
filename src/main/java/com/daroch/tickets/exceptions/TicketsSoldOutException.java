package com.daroch.tickets.exceptions;

public class TicketsSoldOutException extends RuntimeException {

  // No-arg constructor
  public TicketsSoldOutException() {
    super();
  }

  // Constructor with message
  public TicketsSoldOutException(String message) {
    super(message);
  }

  // Constructor with message and cause
  public TicketsSoldOutException(String message, Throwable cause) {
    super(message, cause);
  }

  // Constructor with cause only
  public TicketsSoldOutException(Throwable cause) {
    super(cause);
  }

  // Full constructor
  public TicketsSoldOutException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
