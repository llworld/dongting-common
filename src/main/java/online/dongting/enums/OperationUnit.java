package online.dongting.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 操作模块.
 *
 * @author ll
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OperationUnit {

  SYSTEM("SYSTEM", "系统模块"),

  WECHAT("WECHAT", "企业微信模块"),


  UNKNOWN("UNKNOWN", "未知操作模块");


  private String value;

  private String text;
}
