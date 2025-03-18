package online.dongting.annotation;



import online.dongting.enums.OperationType;
import online.dongting.enums.OperationUnit;

import java.lang.annotation.*;

/**
 * @author ll
 */
@Documented
@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME
)
public @interface Log {

  /**
   * 终端
   */
  String extremity() default "骑行日记";

  /**
   * 操作模块
   */
  OperationUnit operationUnit() default OperationUnit.UNKNOWN;

  /**
   * 操作类型
   */
  OperationType operationType() default OperationType.UNKNOWN;

  /**
   * 详情描述,可使用占位符获取参数:{{构造参数名}}
   */
  String describe() default "";

}
