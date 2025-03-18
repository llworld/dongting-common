package online.dongting.exceptions;

import lombok.Builder;
import lombok.Data;
import online.dongting.enums.SystemErrorType;

@Builder
@Data
public class ApiErrorResult<T> {

  public static final Integer STATE_ERROR = 409;

  private Integer code;

  private String message;

  /**
   * @param errorType 错误类型
   */
  public ApiErrorResult(ErrorType errorType) {
    this.code = errorType.getStatus();
    this.message = errorType.getMessage();
  }

  public ApiErrorResult(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  /**
   * 系统异常类并返回结果数据
   *
   * @return Result
   */
  public static <T> ApiErrorResult<T> fail() {
    return new ApiErrorResult<T>(SystemErrorType.SYSTEM_ERROR);
  }

  /**
   * 自定义返回结果数据
   *
   * @return Result
   */
  public static <T> ApiErrorResult<T> fail(String message) {
    return new ApiErrorResult<T>(STATE_ERROR, message);
  }

  /**
   * 自定义返回结果数据
   *
   * @return Result
   */
  public static <T> ApiErrorResult<T> fail(Integer code , String message) {
    return new ApiErrorResult<T>(code, message);
  }

  /**
   * token解析失败返回结果数据.
   *
   * @return Result
   */
  public static <T> ApiErrorResult<T> failToken() {
    return new ApiErrorResult<T>(SystemErrorType.UNAUTHORIZED_ERROR.getStatus(),
        SystemErrorType.UNAUTHORIZED_ERROR.getMessage());
  }

  /**
   * 自定义返回结果数据
   *
   * @return Result
   */
  public static <T> ApiErrorResult<T> fail(ErrorType errorType) {
    return new ApiErrorResult<T>(errorType);
  }
}
