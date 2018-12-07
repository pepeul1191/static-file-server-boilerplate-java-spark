package exceptions;

public class StatusErrorException extends Exception {
  public StatusErrorException() { super(); }
  public StatusErrorException(String message) { super(message); }
  public StatusErrorException(String message, Throwable cause) { super(message, cause); }
  public StatusErrorException(Throwable cause) { super(cause); }
}