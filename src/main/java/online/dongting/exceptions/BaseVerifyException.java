package online.dongting.exceptions;

public class BaseVerifyException extends RuntimeException {
  public BaseVerifyException() {
    super();
  }

  public BaseVerifyException(String message) {
    super(message);
  }

  public BaseVerifyException(String message, Throwable cause) {
    super(message, cause);
  }

  public BaseVerifyException(Throwable cause) {
    super(cause);
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}
