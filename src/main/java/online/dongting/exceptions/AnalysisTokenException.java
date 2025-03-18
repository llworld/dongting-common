package online.dongting.exceptions;

import lombok.Getter;
import online.dongting.enums.SystemErrorType;

@Getter
public class AnalysisTokenException extends RuntimeException {

  /**
   * 异常对应的错误类型
   */
  private final SystemErrorType errorType;

  /**
   * 异常对应的错误类型
   */
  private Object data;

  /**
   * 默认是系统异常
   */
  public AnalysisTokenException() {
    this.errorType = SystemErrorType.UNAUTHORIZED_ERROR;
  }

  public AnalysisTokenException(SystemErrorType errorType) {
    this.errorType = errorType;
  }

  public AnalysisTokenException(SystemErrorType errorType, Object data) {
    this.errorType = errorType;
    this.data = data;
  }

}
