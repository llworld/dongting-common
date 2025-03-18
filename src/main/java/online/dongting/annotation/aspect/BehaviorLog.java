package online.dongting.annotation.aspect;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BehaviorLog {

  private String ip;

  /**
   * 终端
   */
  private String extremity;
  /**
   * 模块名称
   */
  private String operationUnit;
  /**
   * 操作类型
   */
  private String operationType;
  /**
   * 请求路径
   */
  private String path;
  /**
   * 方法名
   */
  private String method;
  /**
   * 请求参数
   */
  private String args;
  /**
   * 操作人id
   */
  private String operationUserId;
  /**
   * 日志描述
   */
  private String describe;

  /**
   * 方法运行时间
   */
  private Long runTime;
  /**
   * 方法返回值
   */
  private String returnValue;
  /**
   * 创建日期
   */
  private String createTime;
}
