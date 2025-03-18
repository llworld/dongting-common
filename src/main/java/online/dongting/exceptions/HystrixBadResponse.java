package online.dongting.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 熔断异常信息实体.
 *
 * @author ll
 */
@Getter
@Setter
@AllArgsConstructor
public class HystrixBadResponse {

  /**
   * 错误信息.
   */
  private String message;
  /**
   * 响应内容.
   */
  private String content;
  /**
   * 响应状态吗.
   */
  private Integer status;
}
