package online.dongting.config;

import lombok.extern.slf4j.Slf4j;
import online.dongting.constant.SystemConst;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**
 * 拦截器，针对于需要认证的接口需要进行拦截.
 */
@Slf4j
public class WebInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) throws IOException {
    //当前用户id
    String userId = request.getHeader(SystemConst.CURRENT_USER_ID_HEADER);
//    if (StrUtil.isBlank(userId)) {
//      response.setHeader("content-type", "application/json");
//      response.setCharacterEncoding("UTF-8");
//      response.getWriter().print(JSON.toJSONString(ApiErrorResult.failToken()));
//      response.setStatus(HttpStatus.UNAUTHORIZED.value());
//      response.flushBuffer();
//      return true;
//    }
    return true;
  }
}
