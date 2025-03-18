package online.dongting.exceptions;


import cn.hutool.http.HttpStatus;

/**
 * @author: ll
 * @since: 2023/10/8 HOUR:17 MINUTE:05
 * @description:
 */
public interface ErrorType {

    /**
     * 返回HttpStatus
     *
     * @return int
     */
    default int getStatus(){

        return HttpStatus.HTTP_CONFLICT;
    }

    /**
     * 返回message
     *
     * @return java.lang.String
     */
    String getMessage();
}
