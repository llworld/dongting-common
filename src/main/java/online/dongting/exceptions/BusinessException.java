package online.dongting.exceptions;

import cn.hutool.core.collection.CollUtil;
import online.dongting.enums.SystemErrorType;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author: ll
 * @since: 2023/10/8 HOUR:17 MINUTE:04
 * @description:
 */
public class BusinessException extends RuntimeException {

    private final ErrorType errorType;

    public BusinessException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }


    public BusinessException(String message) {
        super(message);
        this.errorType = SystemErrorType.ERROR;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public static void isTrue(boolean expression,ErrorType errorType) {
        if(expression){
            throw new BusinessException(errorType);
        }
    }

    public static void isFalse(boolean expression,ErrorType errorType) {
        if(!expression){
            throw new BusinessException(errorType);
        }
    }

    public static void isNull(Object object,ErrorType errorType) {
        if(Objects.isNull(object)){
            throw new BusinessException(errorType);
        }
    }

    public static void isNonNull(Object object,ErrorType errorType) {
        if(Objects.nonNull(object)){
            throw new BusinessException(errorType);
        }
    }

    public static void isNotEmpty(Collection<?> collection, ErrorType errorType) {
        if(CollUtil.isNotEmpty(collection)){
            throw new BusinessException(errorType);
        }
    }

    public static void isNotEmpty(Map<?, ?> map, ErrorType errorType) {
        if(CollUtil.isNotEmpty(map)){
            throw new BusinessException(errorType);
        }
    }

    public static void isEmpty(Collection<?> collection, ErrorType errorType) {
        if(CollUtil.isEmpty(collection)){
            throw new BusinessException(errorType);
        }
    }

    public static void isEmpty(Map<?, ?> map, ErrorType errorType) {
        if(CollUtil.isEmpty(map)){
            throw new BusinessException(errorType);
        }
    }
}
