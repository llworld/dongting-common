package online.dongting.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: ll
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OperationType {

  INSERT("INSERT", "新增"),

  UPDATE("UPDATE", "修改"),

  DELETE("DELETE", "删除"),

  PUBLISHED("PUBLISHED", "发布"),

  CLOSE("CLOSE", "关闭"),

  DISABLE("DISABLE", "封禁"),

  ENABLE("ENABLE", "解禁"),

  RECOMMEND("RECOMMEND","推荐"),

  UNRECOMMEND("RECOMMEND","取消推荐"),

  BATCHINSERT("BATCHINSERT", "批量添加"),

  BATCHUPDATE("BATCHUPDATE", "批量修改"),

  BATCHDELETE("BATCHDELETE", "批量删除"),

  BATCHIMPORT("BATCHIMPORT", "批量导入"),

  BATCHEXPORT("BATCHEXPORT", "批量导出"),

  UNKNOWN("UNKNOWN", "未知操作类型"),

  EMPOWERMENT("EMPOWERMENT","赋权");
  private String value;

  private String text;


}
