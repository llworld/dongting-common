package online.dongting.annotation.aspect;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import online.dongting.annotation.Log;
import online.dongting.constant.SystemConst;
import online.dongting.exceptions.HystrixBadResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @descption ：EFK日志收集... AOP 拦截
 * @author ： ll
 */
@Aspect
@Component
public class BehaviorLogAspect {

  static final Logger logger = LoggerFactory.getLogger(BehaviorLogAspect.class);

  /**
   * @描述： 切入点
   */
  @Pointcut("@annotation(online.dongting.annotation.Log)")
  public void operationLog() {
  }

  /**
   * @描述： 环绕增强
   */
  @Around("operationLog()")
  public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

    Object res = null;
    Object proceed = null;
    long time = System.currentTimeMillis();
    try {
      proceed = joinPoint.proceed();
      res = JSON.toJSONString(proceed);
      time = System.currentTimeMillis() - time;
    } catch (HystrixBadRequestException e) {

      HystrixBadResponse hystrixBadRequestResponse = JSONUtil
          .toBean(e.getMessage(), HystrixBadResponse.class);

      res = hystrixBadRequestResponse.getContent();

      time = System.currentTimeMillis() - time;
      throw new HystrixBadRequestException(e.getMessage());
    } finally {
      try {
        //方法执行完成后增加日志
        addOperationLog(joinPoint, res, time);
      } catch (Exception e) {
        System.out.println("LogAspect 操作失败：" + e.getMessage());
        e.printStackTrace();
      }
    }
    return proceed;
  }

  private void addOperationLog(JoinPoint joinPoint, Object res, long time) {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    RequestAttributes ra = RequestContextHolder.getRequestAttributes();
    if(ra == null){
      return;
    }
    ServletRequestAttributes sra = (ServletRequestAttributes) ra;
    HttpServletRequest request = sra.getRequest();

    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Log annotation = signature.getMethod().getAnnotation(Log.class);

    BehaviorLog behaviorLog = BehaviorLog.builder()
        .runTime(time)
        .returnValue((String) res)
        .args(JSON.toJSONString(getNameAndValue(joinPoint)))
        .createTime(sdf.format(new Date()))
        .method(signature.getDeclaringTypeName() + "." + signature.getName())
        .path(request.getServletPath())
        .extremity(annotation.extremity())
        .operationUnit(annotation.operationUnit().getText())
        .operationType(annotation.operationType().getText())
        .operationUserId(request.getHeader(SystemConst.CURRENT_USER_ID_HEADER))
        .describe(getDetail(((MethodSignature) joinPoint.getSignature()).getParameterNames(),
            joinPoint.getArgs(), annotation)).build();

    //保存日志
    logger.info(JSON.toJSONString(behaviorLog));
  }

  /**
   * @描述： 参数名与参数值对应
   */
  Map<String, Object> getNameAndValue(JoinPoint joinPoint) {
    Map<String, Object> param = new HashMap<>();

    Object[] paramValues = joinPoint.getArgs();
    String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();

    for (int i = 0; i < paramNames.length; i++) {
      param.put(paramNames[i], paramValues[i]);
    }
    return param;
  }

  /**
   * @描述： 占位符处理
   */
  private String getDetail(String[] argNames, Object[] args, Log annotation) {

    Map<Object, Object> map = new HashMap<>(4);
    for (int i = 0; i < argNames.length; i++) {
      map.put(argNames[i], args[i]);
    }

    String detail = annotation.describe();
    try {
      for (Map.Entry<Object, Object> entry : map.entrySet()) {
        Object k = entry.getKey();
        Object v = entry.getValue();
        detail = detail.replace("{{" + k + "}}", JSON.toJSONString(v));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return detail;
  }
}
