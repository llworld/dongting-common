package online.dongting.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import online.dongting.exceptions.ErrorType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SystemErrorType implements ErrorType {
  /**
   * 业务异常提示
   **/

  USER_ERROR(409,"系统异常，当前用户不存在."),
  UNAUTHORIZED_ERROR(401, "无权访问!"),
  FILE_EMPTY_ERROR(409,"文件不能为空"),
  SYSTEM_ERROR(409, "主站应用服务系统异常"),
  ERROR(409, "服务器开小差了."),
  PHONE_EXISTS(409, "手机号已存在."),
  ;


  SystemErrorType(String message) {
    this.message = message;
  }


  /**
   * 错误类型码
   */
  private int code;
  /**
   * 错误类型描述信息
   */
  private String message;
}
