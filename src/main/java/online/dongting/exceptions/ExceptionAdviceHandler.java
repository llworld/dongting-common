package online.dongting.exceptions;

import cn.hutool.json.JSONUtil;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.val;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

/**
 * @author: ll
 * @since: 2023/10/8 HOUR:17 MINUTE:37
 * @description:
 */
@Order(-1)
@RestController
@ControllerAdvice
public class ExceptionAdviceHandler {

    /**
     * token解析失败返回的异常.
     */
    @ExceptionHandler(AnalysisTokenException.class)
    @ResponseBody
    ResponseEntity<?> tokenErrorException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiErrorResult.failToken());
    }


    /**
     * 处理409异常，无效的请求数据格式.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    ResponseEntity<?> handleConflictException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiErrorResult.fail(e.getMessage()));
    }

    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> bindExceptionHandler(BindException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiErrorResult.fail(e.getAllErrors().get(0).getDefaultMessage()));
    }

    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationExceptionHandler(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiErrorResult.fail(e.getConstraintViolations().iterator().next().getMessage()));
    }

    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiErrorResult.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

    /***
     * 自定义异常捕捉
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> baseException(BusinessException e) {
        return ResponseEntity.status(e.getErrorType().getStatus()).body(ApiErrorResult.fail(
                e.getErrorType().getStatus(), e.getMessage()
        ));
    }

    /**
     * 400<=和<500之间属于业务异常，进行捕获.
     */
    @ExceptionHandler(HystrixBadRequestException.class)
    @ResponseBody
    ResponseEntity<?> handleNotFoundException(HystrixBadRequestException e) {
        val hystrixBadResponse = JSONUtil.toBean(e.getMessage(), HystrixBadResponse.class);
        ApiErrorResult<?> apiErrorResult;
        try {
            apiErrorResult = JSONUtil
                    .toBean(hystrixBadResponse.getContent(), ApiErrorResult.class);
        } catch (Exception ex) {
            apiErrorResult = new ApiErrorResult(hystrixBadResponse.getStatus(), hystrixBadResponse.getContent());
        }
        if (Objects.nonNull(apiErrorResult.getCode())) {
            return ResponseEntity.status(apiErrorResult.getCode()).body(apiErrorResult);
        }
        return ResponseEntity.status(hystrixBadResponse.getStatus())
                .body(hystrixBadResponse.getContent());
    }

    /**
     * 处理自定义的校验运行异常.
     */
    @ExceptionHandler(BaseVerifyException.class)
    @ResponseBody
    ResponseEntity<?> handleBaseVerifyException(BaseVerifyException e) {
        ApiErrorResult<?> result = ApiErrorResult.builder().code(HttpStatus.CONFLICT.value()).message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }




}
