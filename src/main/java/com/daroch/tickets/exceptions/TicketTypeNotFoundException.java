package com.daroch.tickets.exceptions;

public class TicketTypeNotFoundException extends RuntimeException {

  // No-arg constructor
  public TicketTypeNotFoundException() {
    super();
  }

  // Constructor with message
  public TicketTypeNotFoundException(String message) {
    super(message);
  }

  // Constructor with message and cause
  public TicketTypeNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  // Constructor with cause only
  public TicketTypeNotFoundException(Throwable cause) {
    super(cause);
  }

  // Full constructor
  public TicketTypeNotFoundException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
